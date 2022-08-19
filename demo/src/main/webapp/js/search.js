let moviesList;
let moviesItemTemplate;

// Function to search for a movie title and populate html
function searchForTitle(title) {
    fetch(`/omdb/searchName/${title}`)
        .then(resp=>resp = resp.json())
        .then(obj=>{
            //console.log(obj);
            for (let movie of obj) { // populate table
                let a = moviesItemTemplate.cloneNode(true);
                a.setAttribute("imdbId", movie.imdbID);
                a.querySelector("#movieName").textContent = movie.Title+", "+movie.year;
                a.querySelector("#icon").setAttribute("src", movie.poster);
                a.querySelector("#view").setAttribute("imdbId", a.imdbID);
                a.querySelector("#view").onclick = ()=> {
                    console.log("clicked more info, " + movie.imdbID);
                    window.location.href = "movieinfo.html?id=" + movie.imdbID;
                };
                moviesList.appendChild(a);
            }
        })
        .catch(err=>console.log("error encountered while fetching title"));
}

// Clear movies list
function clearMoviesList() {
    while (moviesList.firstChild)
        moviesList.firstChild.remove();
}

// On DOM loaded
document.addEventListener('DOMContentLoaded', () => {
    // set up some stuff
    moviesList = document.querySelector(".movie-search-list")
    let a = document.querySelector(".movie-search-item")
    moviesItemTemplate = a.cloneNode(true);
    a.remove();

    // attach click event to search button
    document.querySelector("#button-movie-search").onclick = ((evt)=>{
        evt.preventDefault();
        clearMoviesList();

        let searchStr = document.querySelector("#input-movie-search").value.toLowerCase();
        if (searchStr !== "")
            searchForTitle(searchStr);
    })
})