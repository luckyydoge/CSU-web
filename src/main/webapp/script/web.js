/*第一组图片切换*/
var imgs1 = [
    'picture/P2/m11.png',
    'picture/P2/m2.png',
    'picture/P2/m3.png',
    'picture/P2/m4.png',
    'picture/P2/m5.png'
]
var index1 = 0;

function left1() {
    var str = '#e'+String(index1)
    document.getElementById(str).style.border='none'
    index1 = (index1 - 1 + imgs1.length) % imgs1.length;

    var str = '#e'+String(index1)
    document.getElementById(str).style.border='3px solid blue'
    document.getElementById('show').src = imgs1[index1];
}

function right1() {
    var str = '#e'+String(index1)
    document.getElementById(str).style.border='none'
    index1 = (index1 + 1 + imgs1.length) % imgs1.length;

    var str = '#e'+String(index1)
    document.getElementById(str).style.border='3px solid blue'
    document.getElementById('show').src = imgs1[index1];
}


/*第二组图片切换*/
var imgs2 = [
    'picture/P4/tt.png',
    'picture/P4/jj.png'
]
var index2 = 0
var index3 = 1

function left2(){
    var index = index2
    index2 = index3
    index3 = index
    document.getElementById('example1').src=imgs2[index2]
    document.getElementById('example2').src=imgs2[index3]
}

function right2(){
    var index = index2
    index2 = index3
    index3 = index
    document.getElementById('example1').src=imgs2[index2]
    document.getElementById('example2').src=imgs2[index3]
}

/*导航栏*/
// let lastScrollTop = 0; // 用来记录上一次滚动位置
//         const nav = document.querySelector('.fix'); // 获取导航栏元素
//         window.addEventListener('scroll', function() {
//         let currentScroll = window.pageYOffset || document.documentElement.scrollTop;
//         if (currentScroll > lastScrollTop)
//         // 当鼠标向下滚动时，隐藏导航栏
//             nav.style.top = '-90px'; 
//         else 
//         // 当鼠标向上滚动时，显示导航栏
//             nav.style.top = '0';
//         lastScrollTop = currentScroll <= 0 ? 0 : currentScroll; 
//         });

       