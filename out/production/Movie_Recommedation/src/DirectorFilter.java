public class DirectorFilter implements Filter{
    private String myDirectors;

    public DirectorFilter(String directors) {
        myDirectors = directors;
    }

    @Override
    public boolean satisfies(String id) {
        String[] directors = myDirectors.split(",");

        for (int k = 0; k < directors.length; k++) {
            if (MovieDatabase.getDirector(id).contains(directors[k])) {
                return true;
            }
        }
        return false;
    }
}
