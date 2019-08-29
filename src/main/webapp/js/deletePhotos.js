$(document).ready(function() {
    $(".photoSaved > a").click(function (event) {
        event.preventDefault();
        var el = $(this),
            photoId = $(this).attr('href');
        $.post("supprimerPhoto.do", {idPhoto: photoId}, function (data) {
            if (data == 'true') {
                el.parent().remove();
                alert("Suppression effectuée");
            } else {
                alert("Suppression échouée");
            }
        })
    });
});