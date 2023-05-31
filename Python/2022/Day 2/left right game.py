# Kyle Dencker
# Python 3
# Game
import random

def print_board(board):
    print("Current Board")

    for num in range(len(board)):
        if (num + 1 == len(board)):
            print(str(board[num]))
        else: 
            print(str(board[num]) + ", " , end ="")
    

def main():
    board = []
    turn = 0
    score1 = 0
    score2 = 0

    for num in range(10):
        board.append( random.randint(1, 10) )

    while (len(board) != 0):
        
        print_board(board)

        print("Player #"+str(turn%2+1))
        choice = int(input("Would you like the left side (1) or right (2)?"))

        if (choice == 1):
            if (turn % 2 == 0):
                score1 += board[0] # score1 = score1 + board[0]
            else:
                score2 += board[0]
            turn += 1
            board.pop(0)
        elif (choice == 2):
            if (turn % 2 == 0):
                score1 += board[-1] # score1 = score1 + board[0]
            else:
                score2 += board[-1]
            turn += 1
            board.pop(-1)

        print("Player 1: "+str(score1) + "\nPlayer 2: "+str(score2))

    if (score1 > score2):
        print("Player 1 Wins by "+str(score1-score2)+" points!")
    elif (score2 > score1):
        print("Player 2 Wins by "+str(score2-score1)+" points!")
    else:
        print("The game tied")

main()
