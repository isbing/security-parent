//layer.msg("设置成功!");
// layer.msg(result.message,{icon:1,time:1000},function(){
//     window.parent.location.reload();
// });
// setTimeout(function(){
//     window.parent.location.reload();
//     var index = window.parent.layer.getFrameIndex(window.name);
//     parent.layer.close(index);//在iframe层关闭自己
// },1000);
layer.msg("设置成功!",{icon:1,time:1000},function(){
    var index = window.parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);//在iframe层关闭自己
});


// 这里用来获取权限 用于判断按钮是否有权限展示
// 先去window.localStorage拿 然后去cookie拿 获取所有的 permission码和role码
var authorities = getAuthoritiesByLocal();
for (var i = 0; i < data.content.length; i++) {
    var updateButton = "";
    // 不存在 返回 -1
    if (authorities.indexOf("ROLE_SECURITY") >= 0) {
        updateButton = "<a onclick='updatePermission(\"" + data.content[i].id + "\")'>修改</a>";
    }
    $("#table tbody").append("<tr>" +
        "<th>" + checkedStr(data.content[i].id) + "</th>" +
        "<th>" + checkedStr(data.content[i].name) + "</th>" +
        "<th>" + checkedStr(data.content[i].code) + "</th>" +
        "<th>" + checkedStr(updateButton) + "</th>" +
        "</tr>");
}





