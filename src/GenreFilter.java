public class GenreFilter implements Filter{
    private String myGenre;

    public GenreFilter(String genre) {
        myGenre = genre;
    }

    /**
     * override method in the filter interface, and
     * return true if satisfies this condition
     * @param id
     * @return
     */
    public boolean satisfies(String id){
        int idx = MovieDatabase.getGenres(id).indexOf(myGenre);
        if (idx != -1){
            return true;
        }else{
            return false;
        }
    }


}
