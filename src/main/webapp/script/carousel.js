const slidesContainer = document.querySelector('.slides');
const indicatorsContainer = document.querySelector('.indicators');
const prevButton = document.querySelector('.prev');
const nextButton = document.querySelector('.next');

let currentIndex = 0;
let images = [];

// 从服务器获取图片列表和图片数量
async function fetchImages() {
    try {
        const response = await fetch('http://localhost:32425/web_last/api/queryImg');
        const data = await response.json();
        images = data.images;
        renderSlides();
        renderIndicators();
    } catch (error) {
        console.error('获取图片失败:', error);
    }
}

// 渲染幻灯片
function renderSlides() {
    slidesContainer.innerHTML = ''; // 清空现有内容
    images.forEach(imageUrl => {
        const slide = document.createElement('div');
        slide.className = 'slide';
        const img = document.createElement('img');
        img.src = imageUrl;
        slide.appendChild(img);
        slidesContainer.appendChild(slide);
    });
}

// 渲染指示器
function renderIndicators() {
    indicatorsContainer.innerHTML = ''; // 清空现有内容
    images.forEach((_, index) => {
        const indicator = document.createElement('span');
        indicator.className = 'indicator';
        if (index === currentIndex) {
            indicator.classList.add('active');
        }
        indicator.addEventListener('click', () => {
            currentIndex = index;
            updateSlides();
        });
        indicatorsContainer.appendChild(indicator);
    });
}

// 更新幻灯片位置
function updateSlides() {
    slidesContainer.style.transform = `translateX(-${currentIndex * 100}%)`;
    updateIndicators();
}

// 更新指示器
function updateIndicators() {
    const indicators = document.querySelectorAll('.indicator');
    indicators.forEach((indicator, index) => {
        indicator.classList.toggle('active', index === currentIndex);
    });
}

// 切换到上一张幻灯片
function prevSlide() {
    currentIndex = (currentIndex - 1 + images.length) % images.length;
    updateSlides();
}

// 切换到下一张幻灯片
function nextSlide() {
    currentIndex = (currentIndex + 1) % images.length;
    updateSlides();
}

// 自动播放
let autoPlayInterval = setInterval(nextSlide, 3000);

// 停止自动播放
function stopAutoPlay() {
    clearInterval(autoPlayInterval);
}

// 重新开始自动播放
function startAutoPlay() {
    autoPlayInterval = setInterval(nextSlide, 3000);
}

// 监听按钮点击事件
prevButton.addEventListener('click', () => {
    stopAutoPlay();
    prevSlide();
    startAutoPlay();
});

nextButton.addEventListener('click', () => {
    stopAutoPlay();
    nextSlide();
    startAutoPlay();
});

// 初始化
fetchImages();