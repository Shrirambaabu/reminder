package in.myreminder.srb.model;

public class MyNotes {

    private String notesId;
    private String notesName;
    private String notesDate;
    private String notesPriority;
    private String notesRead;

    public MyNotes() {
    }

    public String getNotesId() {
        return notesId;
    }

    public void setNotesId(String notesId) {
        this.notesId = notesId;
    }

    public String getNotesName() {
        return notesName;
    }

    public void setNotesName(String notesName) {
        this.notesName = notesName;
    }

    public String getNotesDate() {
        return notesDate;
    }

    public void setNotesDate(String notesDate) {
        this.notesDate = notesDate;
    }

    public String getNotesPriority() {
        return notesPriority;
    }

    public void setNotesPriority(String notesPriority) {
        this.notesPriority = notesPriority;
    }

    public String getNotesRead() {
        return notesRead;
    }

    public void setNotesRead(String notesRead) {
        this.notesRead = notesRead;
    }
}
