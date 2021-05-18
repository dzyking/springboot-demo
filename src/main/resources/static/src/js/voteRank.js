$(function () {
    function getVote() {
        $.ajax({
            url: SERVICE + 'vote/getVoteAchievement',
            type: "get",
            success: function (res) {
                let html = '';
                for (let i = 0; i < res.data.length; i++) {

                    html += '<tr><td>' + (i + 1) + '</td><td>' + res.data[i].name + '</td></tr>'

                }
                $('tbody').html(html);
            }
        });
    }

    getVote();
    setInterval(function () {
        getVote();
    }, 5000)
});
