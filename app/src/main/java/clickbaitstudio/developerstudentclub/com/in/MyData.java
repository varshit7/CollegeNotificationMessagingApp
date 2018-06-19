package clickbaitstudio.developerstudentclub.com.in;

/**
 *
 * By Developer Student Club
 *
 * K Varshit Ratna (lead)
 * Manikanth P (core team member)
 * Deveraj (core team member)
 */
public  class MyData {

    public String description, image_link, date, fromimage, title,id;

    public MyData(String id, String description, String image_link, String date, String title, String fromimage) {
        this.id = id;
        this.description = description;
        this.image_link = image_link;
        this.title = title;
        this.fromimage = fromimage;
        this.date = date;

    }

    public String getId() {return id;}

    public String getDescription() {
        return description;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getfromimage_link() {
        return fromimage;
    }

}


