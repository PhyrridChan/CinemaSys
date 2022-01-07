package Items;

public class Casts {
    private String director;
    private String cast;
    private String producer;
    private String writer;
    private String cameraman;
    private String artDirector;
    private String editor;
    private String sound;
    //develop in the future: info page for each staff
    /**
     * Constructor
     */
    public Casts(String director, String cast, String producer, String writer, String cameraman, String artDirector, String editor, String sound) {
        this.director = director;
        this.cast = cast;
        this.producer = producer;
        this.writer = writer;
        this.cameraman = cameraman;
        this.artDirector = artDirector;
        this.editor = editor;
        this.sound = sound;
    }

    /**
     * Getters and Setters
     */
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCameraman() {
        return cameraman;
    }

    public void setCameraman(String cameraman) {
        this.cameraman = cameraman;
    }

    public String getArtDirector() {
        return artDirector;
    }

    public void setArtDirector(String artDirector) {
        this.artDirector = artDirector;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String toString() {
        return "Casts{" +
                "director='" + director + '\'' +
                ", cast='" + cast + '\'' +
                ", producer='" + producer + '\'' +
                ", writer='" + writer + '\'' +
                ", cameraman='" + cameraman + '\'' +
                ", artDirector='" + artDirector + '\'' +
                ", editor='" + editor + '\'' +
                ", sound='" + sound + '\'' +
                '}';
    }
    public void printInfo(){
        System.out.println(this.toString());
    }
}
