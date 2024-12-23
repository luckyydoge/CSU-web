const imageContainer = document.getElementById('imageContainer');
const reloadButton = document.getElementById('reloadButton');
const hostname = 'http://localhost:32425/web_last/';

// 从服务器获取图片
async function fetchImages() {
    try {
        const response = await fetch(hostname + 'api/queryImg');
        const data = await response.json();
        displayImages(data.data.images);
    } catch (error) {
        console.error('Error fetching images:', error);
    }
}

// 显示图片
function displayImages(images) {
    imageContainer.innerHTML = '';
    images.forEach(image => {
        const imageItem = document.createElement('div');
        imageItem.className = 'image-item';

        const img = document.createElement('img');
        img.src = image;
        // img.alt = image.name;

        const deleteButton = document.createElement('button');
        deleteButton.className = 'delete-button';
        deleteButton.textContent = 'X';
        const url = new URL(image);
        const pathname = url.pathname;
        const filename = pathname.split('/').pop();

        deleteButton.addEventListener('click', () => deleteImage(filename));

        imageItem.appendChild(img);
        imageItem.appendChild(deleteButton);
        imageContainer.appendChild(imageItem);
    });
}

// 删除图片
async function deleteImage(filename) {
    try {
        const response = await fetch(hostname + `admin/file/update?filename=${filename}`, {
            method: 'DELETE'
        });
        if (response.ok) {
            fetchImages(); // 删除成功后重新获取图片
        } else {
            console.error('Failed to delete image');
        }
    } catch (error) {
        console.error('Error deleting image:', error);
    }
}

// 重新加载图片
reloadButton.addEventListener('click', fetchImages);

// 初始加载图片
fetchImages();
// 获取 DOM 元素
const fileInput = document.getElementById('fileInput');
const uploadButton = document.getElementById('uploadButton');
const preview = document.getElementById('preview');
const status = document.getElementById('status');

// 监听文件选择事件
fileInput.addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        // 显示图片预览
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.innerHTML = `<img src="${e.target.result}" alt="Preview" id="previewImage" />`;
        };
        reader.readAsDataURL(file);
    } else {
        preview.innerHTML = '';
    }
});

logoutButton.addEventListener('click', function () {
    fetch(hostname + 'auth/logout', {
        method: 'DELETE',
    }).then(() => {
        window.location.href = 'login.html'
    })

})

// 监听上传按钮点击事件
uploadButton.addEventListener('click', function () {
    const file = fileInput.files[0];
    if (!file) {
        alert('请选择一张图片');
        return;
    }

    // 创建 FormData 对象
    const formData = new FormData();
    formData.append('file', file);

    // 发送 POST 请求上传图片
    fetch(hostname + 'admin/file/update', {
        method: 'POST',
        body: formData,
    })
        .then(response => {

            fileInput.value = '';
            preview.innerHTML = '';
            if (response.ok) {

            } else if(response.status === 409) {
                throw new Error('文件存在，请勿重复上传')

            } else {
                throw new Error('上传失败，请重试');
            }
        })
        .then(() => {
            // 显示上传成功信息
            status.textContent = `上传成功！文件名：` + file.name;
        })
        .catch(error => {
            // 显示上传失败信息
            status.textContent = `上传失败：${error.message}`;
        });
});

const api = '/api/queryNews';
const proxy = hostname + '/proxy';
let news = [];
let urls = [];
news.push(document.getElementById('new1'), document.getElementById('new2'), document.getElementById('new3'))
async function fetchNewURLs () {
    const response = await fetch(hostname + api);
    const json = await response.json();
    urls = json.data;
    displayNews(urls);
}

function displayNews(urls) {
    for (i = 0; i < news.length; i++) {
        let container = news[i];
        let url = urls[i];
        let link = document.createElement('a');
        link.href = url;

        // 使用 fetch 获取网页内容
        fetch(proxy + "?url=" +  url)
            .then(response => response.text()) // 获取网页的 HTML 文本
            .then(html => {
                // 创建一个 DOMParser 对象
                const parser = new DOMParser();

                // 将 HTML 文本解析为 DOM 对象
                const doc = parser.parseFromString(html, 'text/html');

                // 提取新闻标题
                const title = doc.querySelector('h1')?.textContent || '未找到标题';
                console.log('新闻标题:', title);
                link.text = title;
            })
            .catch(error => {
                console.error('请求失败:', error);
            });
        link.style.color = 'blue';
        link.onmouseover = function () {
            link.style.color = 'red'; // 修改文字颜色
            // link.style.fontSize = '16px';
            link.style.textDecoration = 'underline'; // 添加下划线
        };

        // 鼠标移出时恢复样式
        link.onmouseout = function () {
            link.style.color = 'blue'; // 恢复文字颜色
            link.style.textDecoration = 'none'; // 移除下划线
            // link.style.fontSize = '16px';
        };
        container.innerHTML = '';
        container.appendChild(link);
    }

}
fetchNewURLs();

function changeNews() {
    const inputs = document.querySelectorAll('.news-input');

    const result = Array.from(inputs).map((input, index) => {
        let value = input.value;
        input.value = '';
        return value.trim() || urls[index];
    });

    displayNews(result);
    urls = result;

}

const newsUpload = "/admin/updateNews";
function uploadNews() {
    fetch(hostname + newsUpload, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(urls)
    }).then(() => {
        status.textContent = '新闻上传成功';
    })



}