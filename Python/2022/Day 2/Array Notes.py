# Kyle Dencker
# Python 3
# Array Practice

def main():
    board = []
    board.append("A")
    board.append("B")
    board.append("C")

    board.insert(0, "As easy as...")

    # Remove removes the first item with this value.
    if ("D" in board):
        board.remove("D")
    else:
        print("D is not in the list")

    # Pop removes at the index. 
    board.pop(0)

    board.pop(-1)
        
    print(board)

    print(board[-2])



main()
