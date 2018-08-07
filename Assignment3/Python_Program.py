#Name: Sams Khan
#Class: Programming language concepts
# Assignnment: 3


class Book(object):
    #Attributes: an author, a title and a number of pages

    def __init__(self, author, title, pages = 0):
        self.author = author
        self.title = title
        self.pages = pages

    def getAuthor(self):
        return self.author

    def getTitle(self):
        return self.title

    def getPages(self):
        return self.pages

    def setAuthor(self, author):
        self.author = author

    def setTitle(self, title):
        self.title = title

    def setPages(self, pages = 0):
        self.pages = pages

    def showAuthor(self):
        print(self.getAuthor())

    def showTitle(self):
        print(self.getTitle())

    def showPages(self):
        print(self.getPages())

    def showEverything(self):
        print("Author: " + self.getAuthor())
        print("Title: " + self.getTitle())
        print("Pages: ", self.getPages())

    






def main():

    author = input("Hey!, Enter an author name for the book!: ")
    title = input("Enter a title for the book: " )
    pages = input("Enter the number of pages in the book!: " )
        

    book = Book(author, title, pages)

    print("So far you have entered the following for your book: ")
    book.showEverything()

    #yesorno = input("Do you wish to change any of the previous info?")

    print("Now testing the setter methods, by setting Author as John, Title as Tittimuncus and pages as 42")

    book.setAuthor("John")
    book.setTitle("Tittimuncus")
    book.setPages(42)

    print("The Attribute values have been changed to: ")
    book.showEverything()

    


if __name__ == "__main__":
        main()


