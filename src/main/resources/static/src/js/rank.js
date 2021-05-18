$(function () {
    function getAchievement() {
        $.ajax({
            url: SERVICE + 'question/getAchievement',
            type: "get",
            success: function (res) {
                let html = '';
                for (let i = 0; i < res.data.length; i++) {
                    if (i == 0) {
                        html += '<tr><td><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAetJREFUeNpi/P//PwMtAePAWOAX4AEkM0g0az7Dpg0b0QVZcCjmBWJ/Ei1YhU2QCYfip2SExlP8QeQXkAokraHiAmg++AjEx4H4FBB/AOI+LGZtAuL3UPYRYHDNQQ8iYyCOB+JPQLwfiEE2M0LlFgBxLVRMBYvhf6BygUDMB8RfsAXRCyj9HGh7AJB+jCSXDHW5CY7guQbV8wrNLBQLLkJpKSh9B0mOB098gcBlYBCDfCuJZhaKpsNA/A+cgvwCQMFwhYQIvgQNOm5ocB3BtGDThjdA8gCU5w7VRCy4DMQeUPZeoFnvcSXTfigNSlHHkMS/AvFDIP4BxL+g7M9I8meAOAnKnogvo22F+sIBiA2gES0LxGuArkpAUqcADMYJQDofHMEMDM5Q9XuAeAf+osIvQB7qIjYgPgvEjkD8G4i/oTmGE6pmLzR1gdQYAR3ymJiySBdIgsoVRSLjABRkvkDDLxNfmvoFgJJmDhCHAbEhntSzChzumzZ8Ib+49gtoAZLVaKJTgTgXaPB/6tQHfgFpQHImlFcCNLiX8grHL4AZSM6FFn4iSIXhKXCRAilzkoCW/cJlBAsBB6RAC0B0YIbEPoejdCUYyfxA8jYQixJwxAdwMbFpw1tSfeCAlpvxAWdcNRrjkG9VAAQYAPcvnb2TaH65AAAAAElFTkSuQmCC"/></td><td>' + res.data[i].userName + '</td></tr>'
                    } else if (i == 1) {
                        html += '<tr><td><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAcdJREFUeNrklUsohFEUgGcGC4+I0lgJSTHJQlkh2WAxZiFZe20UiZWFYmczCyXlsWFhpZQQC8mOWFDeZWGhPCcKU+MxvlN36u/vf/lrVm59nemcc8+559x7/vHG43FPMpfPk+SV9ASpZobPsH8ZUeowzk3a8H3oTwlY2VDlMMGrmxY9GOjuIGKgvzUL4tW+Itqyi6g38JOgtXAJsqEXZkxi7tCuRrMKEqebhjGdbQpiECbALPJZYxvRJIxYtehKyaicRKPPAzlVitwNlWYhMzT2TfhQvy+sEuwpGYATg/KvYRTGIV3pvuEMynUxDBNsqdNLv9/hSRe8AXpgSKOXe/FCHbzBtmkCeitlzoO0oB32lelcJW2CNjiGH2U7UL6ZMEeMqOkrUi+pAHGqLkuSTcAkGwd1fi+IHOiHAciFCvweLROoza2IFTiEGpshkwqqIUTwNUeDhuMqIgjFDqZYfIJGwS0nmQ0biBLohCMDF3k5XZJA+dpPssWHLx+xAC2JaYUOfb9dfa4JXqjmYlGjXhIdtiK7/ZYVEMCn2lNp4iKTH6CSL7cVdFsEl1UGfa4q4PTyX7EOfptDyLw0U0XM9SX/7z/9XwEGANfpjeUYRnV1AAAAAElFTkSuQmCC"/></td><td>' + res.data[i].userName + '</td></tr>'
                    } else if (i == 2) {
                        html += '<tr><td><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAZhJREFUeNrslc0rhFEUh2d8JAusZuOjpFlYkBIpC6TsLDQJCzaUhY3SFLGglI1S/AH2UjZYTDYSsfCR8ZWtJJooZJQZxnPrqBO9894ZzUI59XSmc+85v3vuvXNfbyKR8GTSsjwZtr8vkOM0EFvxNeBClnU6cgORzZQEsHsoshS4te6AlQ/gCiD/29AG7EAZ9EKeGguQF8U/0cmiWwdj4JeCr0rIFC+EaTiDOYm/QAu0wSUsuh3ytfhhOFLxKRiBOoiq+J7EdW5SgQPxflnRl63K9oRhUsVPoEJ+79sIrIlvglMVb5Vtq4Z+FQ/LXGPrNgJbcAE9sjpjb3AFldLFnZp/DN2ymG1XAW7BBy4IpVAPzxCHTjlkU2RBpj9IZ2ZukNwfD5vX6bHj2s2IUASKHa75DfhgluITKT0VJIzjhsDtuR10Kp60A9WJF9cOS+o/EYM+WJYt9aQtoIRq1RVupPCuTZ5NB824eciGKgmbG/MOowiF0haguDmjQ6hxmHJuxhCJp/s96IJyeHSgRB6+35/B/zc5Y/YpwAAXg35Gi0o3UQAAAABJRU5ErkJggg=="/></td><td>' + res.data[i].userName + '</td></tr>'
                    } else {
                        html += '<tr><td>' + (i + 1) + '</td><td>' + res.data[i].userName + '</td></tr>'
                    }
                }
                $('#before tbody').html(html);
            }
        });
    }

    getAchievement();
    setInterval(function () {
        getAchievement();
    }, 5000)

});
