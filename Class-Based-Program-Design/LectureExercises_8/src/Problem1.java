/* Problem 1


Suppose you are working on a research paper, and you have gathered a set of documents together for your bibliography: books and Wikipedia articles.
Every document has an author, a title, and a bibliography of documents;
additionally, books have publishers, and wiki articles have URLs.

- Since you know that wiki articles are not necessarily authoritative sources(citation needed),
  you want to produce a bibliography containing just the authors and titles of the books you’ve found,
  either directly or transitively through the bibliographies of other documents.
  Format the entries as “Last name, First name. "Title".”

- Since bibliographies must be alphabetized, sort the bibliography by the authors’ last names.

- Documents may be referenced more than once, but should only appear in the bibliography once. Remove any duplicates (defined as the same author name and the same title)

*/
import tester.Tester;

class Author{
  String    name;
  String surname;

  Author(String name, String surname){
    this.name=name;
    this.surname=surname;
  }

  public String biography(){
    /*Fields
     * name     -- String
     * surname -- String
     */
    return this.surname + ", " + this.name + ". "; 
  }

  public String getSurname(){
    /*Fields
     * name     -- String
     * surname  -- String
     * Methods 
     * this.biography()  -- String
     * this.getAuthor()  -- Author
     */
    return this.surname;
  }

  // return true if the current author surname is > than the given author one; false otherwise
  public boolean sortAuthor(Author author){
    /*Fields
     * name     -- String
     * surname  -- String
     * Methods 
     * this.biography()  -- String
     * this.getAuthor()  -- Author
     * this.getSurname() -- String
     */
    return this.surname.compareTo(author.getSurname()) > 0;
  }

  public String getName(){
    /*Fields
     * name     -- String
     * surname  -- String
     * Methods 
     * this.biography()  -- String
     * this.getAuthor()  -- Author
     * this.getSurname() -- String
     * this.sortAuthor() -- boolean
     */
    return this.name;
  }

  public boolean sameAuthor(Author author){
    /*Fields
     * name     -- String
     * surname  -- String
     * Methods 
     * this.biography()  -- String
     * this.getAuthor()  -- Author
     * this.getSurname() -- String
     * this.sortAuthor() -- boolean
     * this.getName() -- String
     */
    return this.name.equals(author.getName()) && this.surname.equals(author.getSurname());
  }

}

interface IDocument {
  String biography();
  String biographyHelp();
  boolean       isWiki();
  Author getAuthor();
  ILoDocument sortBibliography();
  boolean sortAuthor(Author author);

  ILoDocument noDuplicate();

  boolean sameAuthor(Author a);
  boolean sameTitle(IDocument doc);
  String getTitle();
}

class Book implements IDocument{
  Author    author;
  String     title;
  ILoDocument  lod;
  String publisher;

  Book(Author author, String title, ILoDocument lod, String publisher){
    this.author=author;
    this.title=title;
    this.lod=lod;
    this.publisher=publisher;
  }

  // return biography of docmunets, call the function on the list of documents.
  public String biography(){
   /*Field
    this.author    -- String
    this.title     -- String
    this.lod       -- ILoDocument
    this.publisher -- String
   */
    return this.lod.biography();
  }

  public String biographyHelp(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.publisher   -- String
    Methods
    this.biography() -- String
   */
    return this.author.biography() + "'" + this.title + "'";
  }

  public boolean isWiki(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.publisher   -- String
    Methods
    this.biography() -- String
    this.biographyHelp() -- String
   */
    return false;
  }

  public Author getAuthor(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.publisher   -- String
    Methods
    this.biography()     -- String
    this.biographyHelp() -- String
    this.isWiki()        -- boolean
   */
    return this.author;
  }

  public boolean sortAuthor(Author author){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.publisher   -- String
    Methods
    this.biography()     -- String
    this.biographyHelp() -- String
    this.isWiki()        -- boolean
    this.getAuthor()     -- Author
   */
    return this.author.sortAuthor(author);
  }

  public ILoDocument sortBibliography(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.publisher   -- String
    Methods
    this.biography()     -- String
    this.biographyHelp() -- String
    this.isWiki()        -- boolean
    this.getAuthor()     -- Author
    this.sortAuthor()    -- boolean
   */
    return this.lod.sortBibliography();
  }

  public ILoDocument noDuplicate(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.publisher   -- String
    Methods
    this.biography()        -- String
    this.biographyHelp()    -- String
    this.isWiki()           -- boolean
    this.getAuthor()        -- Author
    this.sortAuthor()       -- boolean
    this.sortBibliography() -- ILoDocument
   */
    return this.lod.noDuplicate();
  }

    public boolean sameAuthor(Author a){
     /*Field
      this.author      -- String
      this.title       -- String
      this.lod         -- ILoDocument
      this.publisher   -- String
      Methods
      this.biography()        -- String
      this.biographyHelp()    -- String
      this.isWiki()           -- boolean
      this.getAuthor()        -- Author
      this.sortAuthor()       -- boolean
      this.sortBibliography() -- ILoDocument
     */
      return this.author.sameAuthor(a);
    }

  public String getTitle(){
     /*Field
      this.author      -- String
      this.title       -- String
      this.lod         -- ILoDocument
      this.publisher   -- String
      Methods
      this.biography()        -- String
      this.biographyHelp()    -- String
      this.isWiki()           -- boolean
      this.getAuthor()        -- Author
      this.sortAuthor()       -- boolean
      this.sortBibliography() -- ILoDocument
     */
    return this.title;
  }

  public boolean sameTitle(IDocument doc){
     /*Field
      this.author      -- String
      this.title       -- String
      this.lod         -- ILoDocument
      this.publisher   -- String
      Methods
      this.biography()        -- String
      this.biographyHelp()    -- String
      this.isWiki()           -- boolean
      this.getAuthor()        -- Author
      this.sortAuthor()       -- boolean
      this.sortBibliography() -- ILoDocument
      this.getTitle()         -- String
     */
    return this.title.equals(doc.getTitle());
  }
}

class Wiki implements IDocument{
  Author author;
  String title;
  ILoDocument lod;
  String url;

  Wiki(Author author, String title, ILoDocument lod, String url){
    this.author=author;
    this.title=title;
    this.lod=lod;
    this.url=url;
  }

  public String biography(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    */
    return this.lod.biography();
  }

  public String biographyHelp(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    Methods
    this.biography() -- String
    */
    return "";
  }


  public boolean isWiki(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    Methods
    this.biography() -- String
    this.biographyHelp() -- String
   */
    return true;
  }

  public Author getAuthor(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    Methods
    this.biography()     -- String
    this.biographyHelp() -- String
    this.isWiki()        -- boolean
   */
    return this.author;
  }

  public boolean sortAuthor(Author author){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    Methods
    this.biography()     -- String
    this.biographyHelp() -- String
    this.isWiki()        -- boolean
   */
    return this.author.sortAuthor(author);
  }

  public ILoDocument sortBibliography(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    Methods
    this.biography()     -- String
    this.biographyHelp() -- String
    this.isWiki()        -- boolean
    this.getAuthor()     -- Author
    this.sortAuthor()    -- boolean
   */
    return this.lod.sortBibliography();
  }

  public ILoDocument noDuplicate(){
   /*Field
    this.author      -- String
    this.title       -- String
    this.lod         -- ILoDocument
    this.url         -- String
    Methods
    this.biography()        -- String
    this.biographyHelp()    -- String
    this.isWiki()           -- boolean
    this.getAuthor()        -- Author
    this.sortAuthor()       -- boolean
    this.sortBibliography() -- ILoDocument
   */
    return this.lod.noDuplicate();
  }

    public boolean sameAuthor(Author a){
     /*Field
      this.author      -- String
      this.title       -- String
      this.lod         -- ILoDocument
      this.url         -- String
      Methods
      this.biography()        -- String
      this.biographyHelp()    -- String
      this.isWiki()           -- boolean
      this.getAuthor()        -- Author
      this.sortAuthor()       -- boolean
      this.sortBibliography() -- ILoDocument
     */
      return this.author.sameAuthor(a);
    }

  public String getTitle(){
     /*Field
      this.author      -- String
      this.title       -- String
      this.lod         -- ILoDocument
      this.url         -- String
      Methods
      this.biography()        -- String
      this.biographyHelp()    -- String
      this.isWiki()           -- boolean
      this.getAuthor()        -- Author
      this.sortAuthor()       -- boolean
      this.sortBibliography() -- ILoDocument
      this.sameAuthor()       -- Boolean
     */
    return this.title;
  }

  public boolean sameTitle(IDocument doc){
     /*Field
      this.author      -- String
      this.title       -- String
      this.lod         -- ILoDocument
      this.publisher   -- String
      Methods
      this.biography()        -- String
      this.biographyHelp()    -- String
      this.isWiki()           -- boolean
      this.getAuthor()        -- Author
      this.sortAuthor()       -- boolean
      this.sortBibliography() -- ILoDocument
      this.getTitle()         -- String
     */
    return this.title.equals(doc.getTitle());
  }
}

interface ILoDocument{
  String biography();
  String biographyHelp();
  boolean isEmpty();
  ILoDocument filterWiki();

  ILoDocument sortBibliography();
  ILoDocument insert(IDocument doc);

  ILoDocument noDuplicate();
  boolean isUnique(IDocument doc);
}


class ConsLoDocument implements ILoDocument{
  IDocument   first;
  ILoDocument  rest;

  ConsLoDocument(IDocument first, ILoDocument rest){
    this.first=first;
    this.rest=  rest;
  }

  public String biography(){
   /*Field
    * this.first  -- IDocument
    * this.rest   -- ILoDocument
    * Method
    * this.biography() -- String
    * Method on field
    * this.first.biography()     -- String
    * this.first.biographyHelp() -- String
    * this.first.isWiki()        -- boolean
    * this.rest.biography()      -- String
    */
    return this.filterWiki().biographyHelp();
  }

  public boolean isEmpty(){
     return false;
  }

  public String biographyHelp(){
   /*Field
    * this.first  -- IDocument
    * this.rest   -- ILoDocument
    */
    if(this.rest.isEmpty()){
      return this.first.biographyHelp() + this.rest.biography();
    }else{
      return this.first.biographyHelp() +  ", " + this.rest.biography();
    }
  }

  public ILoDocument filterWiki(){
   /*Field
    * this.first  -- IDocument
    * this.rest   -- ILoDocument
    * Method
    * this.biography() -- String
    * Method on field
    * this.first.biography()     -- String
    * this.first.biographyHelp() -- String
    * this.first.isWiki()        -- boolean
    * this.rest.biography()      -- String
    */
    if(this.first.isWiki()){
      return this.rest.filterWiki();
    }else{
      return new ConsLoDocument(this.first, this.rest.filterWiki());
    }
  }

  public ILoDocument sortBibliography(){
   /*Field
    * this.first  -- IDocument
    * this.rest   -- ILoDocument
    * Method
    * this.biography()  -- String
    * this.filterWiki() -- ILoDocument
    * Method on field
    * this.first.biography()     -- String
    * this.first.biographyHelp() -- String
    * this.first.isWiki()        -- boolean
    * this.rest.biography()      -- String
    * this.rest.filterWiki()     -- ILoDocument
    */

    return this.rest.filterWiki().sortBibliography().insert(this.first);
  }

  public ILoDocument insert(IDocument doc){

   /*Field
    * this.first  -- IDocument
    * this.rest   -- ILoDocument
    * Method
    * this.biography()  -- String
    * this.filterWiki() -- ILoDocument
    * Method on field
    * this.first.biography()       -- String
    * this.first.biographyHelp()   -- String
    * this.first.isWiki()          -- boolean
    * this.rest.biography()        -- String
    * this.rest.filterWiki()       -- ILoDocument
    * this.rest.sortBibliography() -- ILoDocument
    */

    if(this.first.sortAuthor(doc.getAuthor())){
      return new ConsLoDocument(doc, this.rest.insert(this.first));
    }else{
      return new ConsLoDocument(this.first, this.rest.insert(doc));
    }
  }

  public ILoDocument noDuplicate(){
    if(this.rest.isUnique(this.first)){
      return new ConsLoDocument(this.first, this.rest.noDuplicate());
    }else{
      return this.rest.noDuplicate();
    }
  }

  public boolean isUnique(IDocument doc){
    if(this.first.sameAuthor(doc.getAuthor()) && this.first.sameTitle(doc)){
      return false;
    }else{
      return this.rest.isUnique(doc);
    }
  }
}



class MtLoDocument implements ILoDocument{
  MtLoDocument(){}

  public String biography(){
    return "";
  }

  public String biographyHelp(){
    return "";
  }

  public boolean isEmpty(){
     return true;
  }

  public ILoDocument filterWiki(){
    return this;
  }

  public ILoDocument sortBibliography(){
    return this;
  }

  public ILoDocument insert(IDocument doc){
    return new ConsLoDocument(doc, this);
  }

  public ILoDocument noDuplicate(){
    return this;
  };
  
  public boolean isUnique(IDocument doc){
    return true;
  }
 }


class ExamplesBibliography{
  ExamplesBibliography(){}

  Author a1 = new Author("Name 1", "Surname 1");
  Author a2 = new Author("Name 2", "Surname 2");
  Author a3 = new Author("Name 3", "Surname 3");

  Author a4 = new Author("Name 1", "A");
  Author a5 = new Author("Name 2", "D");
  Author a6 = new Author("Name 3", "Z");
  Author a7 = new Author("Name 4", "C");
  Author a8 = new Author("Name 5", "W");

  IDocument b4 = new Book(this.a4, "Title 4", this.empty, "Publisher 4");
  IDocument b5 = new Book(this.a5, "Title 5", this.empty, "Publisher 5");
  IDocument b6 = new Book(this.a6, "Title 6", this.empty, "Publisher 6");
  IDocument b7 = new Book(this.a7, "Title 7", this.empty, "Publisher 7");
  IDocument b8 = new Book(this.a8, "Title 8", this.empty, "Publisher 8");

  ILoDocument empty = new MtLoDocument();

  IDocument b1 = new Book(this.a1, "Title 1", this.empty, "Publisher 1"); 
  ILoDocument lod1 = new ConsLoDocument(this.b1, this.empty);

  IDocument b2 = new Book(this.a2, "Title 2",  this.lod1, "Publisher 2");
  ILoDocument lod2 = new ConsLoDocument(this.b1, new ConsLoDocument(this.b2, this.empty)); 

  IDocument b3 = new Book(this.a3, "Title 3",  this.lod2, "Publisher 3"); 
  ILoDocument lod3 = new ConsLoDocument(this.b1, new ConsLoDocument(this.b2, new ConsLoDocument(this.b3, this.empty)));


  IDocument w1 = new Wiki(this.a1, "Title 1",  this.lod3, "Url 1");
  ILoDocument lod4 = new ConsLoDocument(this.b1, new ConsLoDocument(this.b2, new ConsLoDocument(this.b3, new ConsLoDocument(this.w1, this.empty))));

  IDocument w2 = new Wiki(this.a2, "Title 2",  this.lod4, "Url 2");
  ILoDocument lod5 = new ConsLoDocument(this.b1, new ConsLoDocument(this.b2, new ConsLoDocument(this.b3, new ConsLoDocument(this.w1, new ConsLoDocument(this.w2, this.empty)))));

  IDocument w3 = new Wiki(this.a3, "Title 3",  this.lod5, "Url 3");
  ILoDocument lod6 = new ConsLoDocument(this.b1, new ConsLoDocument(this.b2, new ConsLoDocument(this.b3, new ConsLoDocument(this.w1, new ConsLoDocument(this.w2, new ConsLoDocument(this.w3, this.empty))))));

  ILoDocument ls = new ConsLoDocument(this.b1,
    new ConsLoDocument(this.b2,
      new ConsLoDocument(this.b3,
        new ConsLoDocument(this.w1,
          new ConsLoDocument(this.w2,
            new ConsLoDocument(this.w3,
              new ConsLoDocument(this.b4,
                new ConsLoDocument(this.b5,
                  new ConsLoDocument(this.b6,
                    new ConsLoDocument(this.b7,
                      new ConsLoDocument(this.b8,
              this.empty)))))))))));

  ILoDocument sortedLs = new ConsLoDocument(this.b4,
    new ConsLoDocument(this.b7,
      new ConsLoDocument(this.b5,
        new ConsLoDocument(this.b1,
          new ConsLoDocument(this.b2,
            new ConsLoDocument(this.b3,
                new ConsLoDocument(this.b8,
                  new ConsLoDocument(this.b6,
                    this.empty))))))));

  Author    crazy_a = new Author("CRAZY", "BITCH");
  IDocument w4 = new Wiki(this.crazy_a, "Crazy title", this.empty, "Crazy url!");

  ILoDocument lu = new ConsLoDocument(this.b4,
    new ConsLoDocument(this.b5,
      new ConsLoDocument(this.b4,
        new ConsLoDocument(this.w4,
        new ConsLoDocument(this.b5,
            new ConsLoDocument(this.b8,
                this.empty))))));

  ILoDocument uniqueLu = new ConsLoDocument(this.b4,
      new ConsLoDocument(this.w4,
        new ConsLoDocument(this.b5,
          new ConsLoDocument(this.b8,
       this.empty))));

  boolean testFilterWiki(Tester t){
    return
      t.checkExpect(this.empty.filterWiki(), this.empty) &&
      t.checkExpect(this.lod1.filterWiki(), this.lod1) &&
      t.checkExpect(this.lod2.filterWiki(), this.lod2) &&
      t.checkExpect(this.lod3.filterWiki(), this.lod3) &&
      t.checkExpect(this.lod4.filterWiki(), this.lod3) &&
      t.checkExpect(this.lod5.filterWiki(), this.lod3) &&
      t.checkExpect(this.lod5.filterWiki(), this.lod3);
  }

  boolean testBiography(Tester t){
    return
      t.checkExpect(this.b1.biography(), "") &&
      t.checkExpect(this.b2.biography(), "Surname 1, Name 1. 'Title 1'") &&
      t.checkExpect(this.b3.biography(), "Surname 1, Name 1. 'Title 1', Surname 2, Name 2. 'Title 2'") &&
      t.checkExpect(this.w1.biography(), "Surname 1, Name 1. 'Title 1', Surname 2, Name 2. 'Title 2', Surname 3, Name 3. 'Title 3'") &&
      t.checkExpect(this.w2.biography(), "Surname 1, Name 1. 'Title 1', Surname 2, Name 2. 'Title 2', Surname 3, Name 3. 'Title 3'") &&
      t.checkExpect(this.w3.biography(), "Surname 1, Name 1. 'Title 1', Surname 2, Name 2. 'Title 2', Surname 3, Name 3. 'Title 3'");
  }

  boolean testSortBibliography(Tester t){
    return
      t.checkExpect(this.b1.sortBibliography(), this.empty) &&
      t.checkExpect(this.b3.sortBibliography(), this.lod2)  &&
      t.checkExpect(this.w1.sortBibliography(), this.lod3)  &&
      t.checkExpect(this.ls.sortBibliography(), this.sortedLs);
  }

  boolean testNoDuplicate(Tester t){
    return
      t.checkExpect(this.b1.noDuplicate(), this.empty) &&
      t.checkExpect(this.b3.noDuplicate(), this.lod2)  &&
      t.checkExpect(this.lu.noDuplicate(), this.uniqueLu);
  }
}
