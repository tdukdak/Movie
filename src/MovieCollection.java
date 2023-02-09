import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++){
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1){
                results.add(movies.get(i));
            }
        }

        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("Enter a cast member: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.substring(0,1).toUpperCase() + searchTerm.substring(1).toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> castMembers = new ArrayList<String>();

        for (int i = 0; i < movies.size(); i++) {
            String movieCast = movies.get(i).getCast();
            String[] cast = movieCast.split("\\|");
            for(int a = 0; a< cast.length;a++){
                if (cast[a].contains(searchTerm)){
                    if(castMembers.size() == 0){
                        castMembers.add(cast[a]);
                    }
                    else{
                        int count = 0;
                        for (int b = 0; b < castMembers.size();b++){
                            if (castMembers.get(b).equals(cast[a])){
                                count++;
                            }
                            if (b == castMembers.size()-1 && count == 0){
                                castMembers.add(cast[a]);
                            }
                        }
                    }

                }
            }
        }
        Collections.sort(castMembers);
        for (int i = 1;i<=castMembers.size();i++){
            System.out.println(i + ". " + castMembers.get(i-1));
        }
        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        searchTerm = castMembers.get(choice-1);

        for (int i = 0; i < movies.size(); i++) {
            String movieCast = movies.get(i).getCast();
            String[] cast = movieCast.split("\\|");
            for(int a = 0; a< cast.length;a++){
                if (cast[a].equals(searchTerm)){
                    results.add(movies.get(i));
                }
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int ans = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(ans - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            String movieKeywords = movies.get(i).getKeywords();
            movieKeywords = movieKeywords.toLowerCase();

            if (movieKeywords.indexOf(searchTerm) != -1) {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            String movieGenres = movies.get(i).getGenres();
            String[] gen = movieGenres.split("\\|");
            for(int a = 0; a< gen.length;a++){
                if(genres.size() == 0){
                    genres.add(gen[a]);
                }
                else{
                    int count = 0;
                    for (int b = 0; b < genres.size();b++){
                        if (genres.get(b).equals(gen[a])){
                            count++;
                        }
                        if (b == genres.size()-1 && count == 0){
                            genres.add(gen[a]);
                        }
                    }
                }
            }
        }
        Collections.sort(genres);
        for (int i = 1;i<=genres.size();i++){
            System.out.println(i + ". " + genres.get(i-1));
        }

        System.out.println("Choose a genre: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String searchTerm = genres.get(choice-1);
        for (int i = 0; i < movies.size(); i++) {
            String genMovies = movies.get(i).getGenres();
            String[] gen = genMovies.split("\\|");
            for(int a = 0; a< gen.length;a++){
                if (gen[a].equals(searchTerm)){
                    if(results.size() == 0){
                        results.add(movies.get(i));
                    }
                    else{
                        int count = 0;
                        for (int b = 0; b < results.size();b++){
                            if (results.get(b).equals(movies.get(i))){
                                count++;
                            }
                            if (b == results.size()-1 && count == 0){
                                results.add(movies.get(i));
                            }
                        }
                    }

                }
            }
        }
        sortResults(results);
        for (int i = 1;i<=results.size();i++){
            System.out.println(i + ". " + results.get(i-1).getTitle());
        }
        System.out.println("Choose a title: ");
        int ans = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(ans - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Double> ratings = new ArrayList<Double>();
        for (Movie movie : movies)
        {
            ratings.add(movie.getUserRating());
        }
        ratings.sort(Double::compareTo);
        Collections.sort(ratings, Collections.reverseOrder());

        ArrayList<Movie> highestRated = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++){
            for (Movie movie : movies){
                if (movie.getUserRating() == ratings.get(i)){
                    if (highestRated.indexOf(movie) == -1){
                        highestRated.add(movie);
                        break;
                    }
                }
            }
        }

        int i = 0;
        for (Movie movie: highestRated){
            i++;
            System.out.println(i + ". " + movie.getTitle() + ": " + movie.getUserRating());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > highestRated.size() || choice < 1){
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = highestRated.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRevenue()
    {
        ArrayList<Integer> revenue = new ArrayList<Integer>();
        for (Movie movie : movies){
            revenue.add(movie.getRevenue());
        }
        revenue.sort(Integer::compareTo);
        Collections.sort(revenue, Collections.reverseOrder());
        ArrayList<Movie> highestRev = new ArrayList<Movie>();
        for (int i = 0; i < 50; i++){
            for (Movie movie : movies){
                if (movie.getRevenue() == revenue.get(i)){
                    if (highestRev.indexOf(movie) == -1){
                        highestRev.add(movie);
                        break;
                    }
                }
            }
        }

        int i = 0;
        for (Movie movie: highestRev){
            i++;
            System.out.println(i + ". " + movie.getTitle() + ": $" + movie.getRevenue());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice > highestRev.size() || choice < 1){
            System.out.print("Enter number: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        Movie selectedMovie = highestRev.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}