# Kyle Dencker
# Python 3
# Jun 10

class Book:

    def __init__(self, author="Kyle Dencker", title = "The rise and fall of the Denckkonian Empire", pages= 1000000): 
        self.author = author
        self.title = title
        self.pages = pages

    def display(self):
        print("Book Title")
        print("Author: " + self.author)
        print("Title: " + self.title)
        print("Pages: " + str(self.pages))

    def burn(self, pages=1):
        self.pages = self.pages-pages
        if (self.pages < 0):
            self.pages = 0

    def write(self, pages = 1):
        self.pages += pages


b1 = Book()

b1.display()

b2 = Book("Robert Jordan", "Eye of the World", 850)

b2.display()

print()

b2.burn(30)
b1.write(100000)

b1.display()
b2.display()
