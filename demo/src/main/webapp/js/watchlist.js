let moviesList;
let posterTemplate;

function searchForWatchlist(){
    fetch(`/watchlist/user`)
        .then((resp)=>resp = resp.json())
        .then(obj=>{
            for (let watchItem of obj) { //go through the list of movies
                fetch(`/omdb/searchId/${watchItem.movieId}`)
                .then((resp)=> resp = resp.json())
                .then(mov=>{
                    let a = posterTemplate.cloneNode(true);
                    a.setAttribute("imdbId", mov.imdbID);
                    a.querySelector("#movieName").textContent = mov.Title+", "+mov.year;
                    a.querySelector("#icon").setAttribute("src", mov.poster);
                    a.querySelector("#icon").onclick = ()=> {
                        console.log("clicked more info, " + mov.imdbID);
                        window.location.href = "movieinfo.html?id=" + mov.imdbID;
                    };
                    a.querySelector("#btn-remove").onclick = ()=> {
                        console.log("rm movie, " + mov.imdbID);
                        fetch(`watchlist/user/delete/movie/${mov.imdbID}`, {
                            method:'DELETE'
                        })
                        .catch((err)=>console.log("failed to delete\n"+err))
                        a.remove();
                    };
                    moviesList.appendChild(a);
                })
            }
        })
        .catch(err=>console.log("error encountered while fetching title " + err));
}

// On DOM loaded
document.addEventListener('DOMContentLoaded', () => {
    moviesList = document.querySelector(".watchlist")
    let a = document.querySelector(".poster")
    posterTemplate = a.cloneNode(true);
    a.remove();
    searchForWatchlist();
})