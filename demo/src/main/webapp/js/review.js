let moviesList;
let reviewTemplate;

// Function to get user reviews
function getUserReviews(){
    fetch(`/review/user`)
        .then((resp)=>resp = resp.json())
        .then(obj=>{
            for (let review of obj) { // Loop through list of reviews
                fetch(`/omdb/searchId/${review.movieId}`)
                    .then((resp)=> resp = resp.json())
                    .then(mov=>{
                        let a = reviewTemplate.cloneNode(true);
                        a.querySelector("#movieName").textContent = mov.Title+", "+mov.year;
                        a.querySelector("#rating").textContent = review.rating + "/5";
                        a.querySelector("#review").textContent = `"${review.review}"`;
                        a.querySelector("#icon").setAttribute("src", mov.poster);
                        a.querySelector("#icon").onclick = ()=> {
                            console.log("clicked more info, " + mov.imdbID);
                            window.location.href = "movieinfo.html?id=" + mov.imdbID;
                        };
                        a.querySelector("#btn-remove").onclick = ()=> {
                            console.log("rm review, " + mov.imdbID);
                            fetch(`review/delete/${mov.imdbID}`, {
                                method:'DELETE'
                            })
                            .catch((err)=>console.log("failed to delete\n"+err))
                            a.remove();
                        };
                        moviesList.appendChild(a);
                    })
            }
        })
        .catch(err=>console.log("error encountered while fetching review" + err));
}

// Clear movies list
function clearMoviesList() {
    while (moviesList.firstChild)
        moviesList.firstChild.remove();
}

// On DOM loaded
document.addEventListener('DOMContentLoaded', () => {
    // set up some stuff
    moviesList = document.querySelector(".review-list")
    let a = document.querySelector(".review-item")
    reviewTemplate = a.cloneNode(true);
    a.remove();

    getUserReviews();
})