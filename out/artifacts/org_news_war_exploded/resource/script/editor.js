function checkEditorAll() {
    if (checkEditorTitle() && checkEditorKeywords() && checkEditorCategory()) {
        return true;
    } else {
        alert("新闻编辑有误");
        return false;
    }
}
function checkEditorTitle() {
    let title = document.getElementById('title');
    let tip = document.getElementById('tip_title');
    let pattern=/^([\u4e00-\u9fa5]|\w)+$/;//只能字母数字和汉字
    let len = escape(title.value).replace(/%u\w{2}/g,"").length;

    if(len >= 40 || !pattern.test(title.value)){
        tip.innerHTML = "请输入适合长度的汉字字母或者数字";
        return false;
    }else{
        tip.innerHTML = "";
        return true;
    }
}
function checkEditorKeywords() {
    let keywords = document.getElementById('keywords');
    let tip = document.getElementById('tip_keywords');
    let pattern=/^([\u4e00-\u9fa5]|\w)+$/;//只能字母数字和汉字
    let len = escape(keywords.value).replace(/%u\w{2}/g,"").length;

    if(len >= 20 || !pattern.test(keywords.value)){
        tip.innerHTML = "请输入适合长度的汉字字母或者数字";
        return false;
    }else{
        tip.innerHTML = "";
        return true;
    }
}
function checkEditorCategory() {
    let category = document.getElementById('categoryId');
    let tip = document.getElementById('tip_category');
    if(category.value === "请选择文章类型："){
        alert("文章类型未选择");
        return false;
    }else{
        tip.innerHTML = "";
        return true;
    }
}


//判断字符是否为空的方法
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}