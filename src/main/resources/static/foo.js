var myScroll;
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
window.onload = function () {
    myScroll = new IScroll('#wrapper', {
        scrollbars: true
    });
    console.dir(myScroll.options);
    $('#wrapper').css("height", window.screen.height - 140);
    myScroll.on('scrollEnd', function () {
        var scroller = $("#scroller");
        console.log("y:" + this.y);
        if (this.y == this.maxScrollY) {
            $.getJSON('/rest/foo/getPages?page=1',
            function(images) {
                for(i = 0; i < images.length; i++){
                    var image = images[i];
                    var div=document.createElement("div")
                    var img=document.createElement("img")
                    img.src = "/" + image.src;
                    img.onload = function() {
                        console.log(this.src  + " loaded.")
                        myScroll.refresh();
                     }
                    div.appendChild(img);
                    scroller.append(div);
                }
            });
        }
    });

}