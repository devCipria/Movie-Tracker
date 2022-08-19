// Variables
let poster;
let title;
let desc;
let btnReview;
let btnFavorite;
let btnWatchlist;

let isWatchlisted = false;
let isFavorited = false;

// Basically bruteforcing
function swapToRemove(element, text) {
    element.classList.remove('btn-primary');
    element.classList.add('btn-danger');
    element.textContent = text;
}
function swapToAdd(element, text) {
    element.classList.remove('btn-danger');
    element.classList.add('btn-primary');
    element.textContent = text;
}

// Function to search for a movieId
function searchForTitle(id) {
    fetch(`/omdb/searchId/${id}`)
        .then(resp=>resp = resp.json())
        .then(obj=>{
            poster.setAttribute('src', obj.poster);
            title.innerHTML = obj.title;
            document.querySelector("#movieRated").innerHTML = "Rated: " + obj.rated;
            document.querySelector("#movieYear").innerHTML = "Released: " + obj.released;
            document.querySelector("#movieRuntime").innerHTML = "Length: " + obj.runtime;
            document.querySelector("#movieMetascore").innerHTML = "Rating (Metascore): " + obj.metascore + "/100";
            desc.innerHTML = obj.plot;
        })
        .catch(err=>console.log("error encountered while fetching title"));
}

// Function to check watchlisted or not
async function checkForWatchlisted(id) {
    let ret = false;
    fetch(`/watchlist/user`)
        .then((resp)=>resp = resp.json())
        .then(obj=>{
            for (let item of obj) {
                if (item.movieId == id) {
                    isWatchlisted = true;
                    swapToRemove(btnWatchlist, "Remove from watchlist");
                }
            }
        })
        .catch((err)=>console.log("Failed to pull user watchlist"));
}

async function checkForFavorited(id) {
    let ret = false;
    fetch(`/favorite/user`)
        .then((resp)=>resp = resp.json())
        .then(obj=>{
            for (let item of obj) {
                if (item.movieId == id) {
                    isFavorited = true;
                    swapToRemove(btnFavorite, "Remove from favorites");
                }
            }
        })
        .catch((err)=>console.log("Failed to pull user watchlist"));

}

// On DOM loaded
document.addEventListener('DOMContentLoaded', () => {
    // Setup variables
    poster = document.querySelector("#moviePoster");
    title = document.querySelector("#movieTitle");
    desc = document.querySelector("#movieDesc");
    btnReview = document.querySelector("#review-btn");
    btnFavorite = document.querySelector("#favorites-btn");
    btnWatchlist = document.querySelector("#watchlist-btn");
    
    // Pull movie ID from URL
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    // Check if user has watchlist or favorited
    console.log("Pulling user watchlist..");
    checkForWatchlisted(id);
    checkForFavorited(id);

    let userRating = 0;
    // Review rating stars click event
    document.querySelectorAll(".rating-star").forEach((n)=>{
        n.onclick = () => userRating = n.getAttribute('value');
    });

    // Review button on click
    btnReview.onclick = (evt)=>{
        if (userRating < 1) {
            console.log("Must select user rating");
            return;
        }

        let userReview = document.querySelector("#message-text").value;
        console.log(userRating, userReview);

        // Build data to send
        let data = {
            review: userReview,
            rating: userRating
        }

        // Send POST request to endpoint
        fetch(`/review/create/${id}`, {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
              },
            }).then(()=>{
                
            }).catch((err) => {
                console.log("Failed to POST data");
            })
    }

    // Favorites button on click
    btnFavorite.onclick = (evt)=>{
        if (!isFavorited) {
            fetch(`/favorite/movie/${id}`, {
                method: "POST",
                }).then(()=>{
                    console.log("Sent add to fav req")
                }).catch((err) => {
                    console.log("Failed to POST data");
                })
            swapToRemove(btnFavorite, "Remove from favorites");
            isFavorited = true;
            return;
        }
        
        fetch(`favorite/delete/${id}`, {
                method:'DELETE'
            })
            .catch((err)=>console.log("failed to delete\n"+err))
        swapToAdd(btnFavorite, "Add to favorites");
        isFavorited = false;
    }

    // Watchlist button on click
    btnWatchlist.onclick = (evt)=>{
        if (!isWatchlisted) {
            fetch(`/watchlist/user/create/movie/${id}`, {
                method: "POST",
                }).then(()=>{
                    console.log("Sent add to watchlist req")
                }).catch((err) => {
                    console.log("Failed to POST data");
                })
            swapToRemove(btnWatchlist, "Remove from watchlist");
            isWatchlisted = true;
            return;
        }
        
        fetch(`watchlist/user/delete/movie/${id}`, {
                method:'DELETE'
            })
            .catch((err)=>console.log("failed to delete\n"+err))
        swapToAdd(btnWatchlist, "Add to watchlist");
        isWatchlisted = false;
    }

    searchForTitle(id);
})