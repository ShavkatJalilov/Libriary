$(document).ready(function() {
    $('#loader').hide();
    $("#submit").on("click", function() {
        $("#submit").prop("disabled", true);
        var name = $("#name").val();
        var file = $("#image").val();
        var file1 = $("#file").val();
        var muallif=$("#kitobMuallifi").val();
        var category=$("#category").val();
        var janri=$("#janri").val();
        var data = new FormData($("#form")[0]);
        $('#loader').show();
        if (name === "" || file === "" || file1 === "" || janri === "" || category==="" || muallif==="") {
            $("#submit").prop("disabled", false);
            $('#loader').hide();
            $("#name").css("border-color", "red");
            $("#image").css("border-color", "red");
            $("#janri").css("border-color", "red");
            $("#category").css("border-color", "red");
            $("#kitobMuallifi").css("border-color", "red");
            $("#file").css("border-color", "red");
            $("#error_name").html("Maydonni to'ldiring.");
            $("#error_image").html("Maydonni to'ldiring.");
            $("#error_file").html("Maydonni to'ldiring.");
            $("#error_janri").html("Maydonni to'ldiring.");
            $("#error_category").html("Maydonni to'ldiring.");
            $("#error_muallif").html("Maydonni to'ldiring.");
            $("#error_category").html("Maydonni to'ldiring.");
        } else {
            $("#name").css("border-color", "");
            $("#image").css("border-color", "");
            $("#janri").css("border-color", "");
            $("#category").css("border-color", "");
            $("#kitobMuallifi").css("border-color", "");
            $("#file").css("border-color", "");
            $('#error_name').css('opacity', 0);
            $('#error_file').css('opacity', 0);
            $('#error_muallif').css('opacity', 0);
            $('#error_category').css('opacity', 0);
            $('#error_image').css('opacity', 0);
            $('#error_janri').css('opacity', 0);
             $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                data: data,
                url: "/image/saveImageDetails",
                processData: false,
                contentType: false,
                cache: false,
                success: function(data, statusText, xhr) {
                    console.log(xhr.status);
                    if(xhr.status == "200") {
                        $('#loader').hide();
                        $("#form")[0].reset();
                        $('#success').css('display','block');
                        $("#error").text("");
                        $("#success").html("Maxsulot joylandi.");
                        $('#success').delay(2000).fadeOut('slow');
                    }
                },
                error: function(e) {
                    $('#loader').hide();
                    $('#error').css('display','block');
                    $("#error").html("Xatolik");
                    $('#error').delay(5000).fadeOut('slow');
                    location.reload();
                }
            });
        }
    });
});
