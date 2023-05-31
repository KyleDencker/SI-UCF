# Kyle Dencker
# Foundations of Python
# Python 3
# June 6, 2022

# Starting point of the program.
def main():

    # Print and it's different forms.
    print("Hello UCF")
    print("It will be a great day!")
    print()
    
    # A line without a new line.
    print("Hello UCF", end="")
    print("It will be a great day!")
    print()

    # I can do this all in one line!
    print("Hello UCF\nIt will be a great day!")
    # \n - new line
    # \t - Tab
    # \" - Quotes

    age = int ( input( "What is your age?\n" ) )

    age = age + 5

    print("Your age is " + str(age) + "!")

    gpa = float( input("What is your GPA?\n") )

    gpa = gpa + age

    print("You agist GPA is " + str(gpa) + "!")

    name = str ( input("What is your name?\n") )
    name = name + " Dencker"
    print("Your name is " + name + "!")

    # == - Equal
    # != - Not equal
    # <  - Less than
    # >  - greater than
    # <= - less than or equal to
    # >= - greater than or equal to

    if (age < 21):
        print("You are under 21")
    if (age >= 21):
        print("You are over 21")

    if (age < 21):
        print("You are under 21")
    else:
        print("You are over 21")

    

    
# Just have this to start the program.
main()    
