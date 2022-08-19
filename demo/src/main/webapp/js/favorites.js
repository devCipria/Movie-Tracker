let moviesList;
let posterTemplate;

function getFavList(){
    fetch(`/favorite/user`)
        .then((resp)=>resp = resp.json())
        .then(obj=>{
            for (let fav of obj) { // Loop through list of reviews
                fetch(`/omdb/searchId/${fav.movieId}`)
                    .then((resp)=> resp = resp.json())
                    .then(mov=>{
                        let a = posterTemplate.cloneNode(true);
                        a.querySelector("#movieName").textContent = mov.Title + ", " + mov.year;
                        a.querySelector("#icon").setAttribute("src", mov.poster);
                        a.querySelector("#icon").onclick = ()=> {
                            console.log("clicked more info, " + mov.imdbID);
                            window.location.href = "movieinfo.html?id=" + mov.imdbID;
                        };
                        a.querySelector("#btn-remove").onclick = ()=> {
                            console.log("rm favorites, " + mov.imdbID);
                            fetch(`favorite/delete/${mov.imdbID}`, {
                                method:'DELETE'
                            })
                            .catch((err)=>console.log("failed to delete\n"+err))
                            a.remove();
                        };
                        moviesList.appendChild(a);
                    })
            }
        })
        .catch(err=>console.log("error encountered while fetching favorites\n" + err));
}

// On DOM loaded
document.addEventListener('DOMContentLoaded', () => {
    // set up some stuff
    moviesList = document.querySelector(".favlist")
    let a = document.querySelector(".poster")
    posterTemplate = a.cloneNode(true);
    a.remove();

    getFavList();
})