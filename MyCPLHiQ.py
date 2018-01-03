class Stack():
    def __init__(self):
        self.items = []

    def isEmpty(self):
        return self.items == []

    def push(self, item):
        return self.items.append(item)

    def pop(self):
        return self.items.pop()

    def getElements(self):
        return self.items


def findmyrow(board, emp_hole):
    count = 0
    for x in range(0, 5):
        for y in range(0, x + 1):
            if (count == init_hole):
                board[x][y] = 0
            else:
                board[x][y] = 1
            count = count + 1
    return board


def moves(x, y):
    c = 0
    for i in range(0, 5):
        for j in range(0, i + 1):
            if (i == x and j == y):
                return c
            c = c + 1


def check_possiblemove(board):
    pegmove = []
    for x in range(0, 5):
        for y in range(0, x + 1):
            if (board[x][y] == 0):
                pegmove.append([x, y])
    return pegmove


def mypegs(board, x, y):
    if (board[x][y] == 0):
        return 0
    else:
        return 1


def uploadmymoves(board):
    l = check_possiblemove(board)
    bettersol = []
    for x in range(0, len(l)):
        moves_allowed = l[x]
        for y in range(-2, 4, 2):
            for z in range(-2, 4, 2):
                if (y == 0 and z == 0):
                    continue
                var1 = 0
                var2 = 0
                var1 = moves_allowed[0] + y
                var2 = moves_allowed[1] + z
                if (y == 2 and z == -2): continue
                if (y == -2 and z == 2): continue
                if (var1 >= 0 and var2 >= 0 and var1 < 5 and var2 < 5 and var1 >= var2 and mypegs(board, var1,
                                                                                                       var2) == 1):
                    var1 = int((var1 + moves_allowed[0]) / 2)
                    var2 = int((var2 + moves_allowed[1]) / 2)
                    if (mypegs(board, var1, var2) == 1):
                        bettersol.append([moves(moves_allowed[0] + y, moves_allowed[1] + z), moves(var1, var2),
                                          moves(moves_allowed[0], moves_allowed[1])])
    return bettersol


def jump(board, num, poss_move):
    c = 0
    for i in range(0, 5):
        for j in range(0, i + 1):
            if (c == num):
                board[i][j] = poss_move
            c = c + 1


def checkforsolution(board, checkhole):
    global peg
    if (checkhole == peg):
        if (checkhole == 13):
            print("This is the best possible solution")
            pegmove = stack.getElements()
            z = 0
            for i in range(0, 13):
                print( pegmove[i][0], pegmove[i][1], pegmove[i][2])
                z = 13 - i
            print("Pegs Remaining: ", z)
            return 1
            pegmove = stack.getElements()
        z = 0
        for i in range(0, peg):
            print(pegmove[i][0], pegmove[i][1], pegmove[i][2])
            z = 13 - i
        print("Pegs Remaining: ", z)
        print("Do you want a better solution? Select 1 or 0")
        better_soln = int(input())
        if (better_soln == 1):
            peg = peg + 1
        else:
            return 1

    t = uploadmymoves(board)
    for i in range(len(t)):
        checkhole = checkhole + 1
        jump(board, t[i][0], 0)
        jump(board, t[i][1], 0)
        jump(board, t[i][2], 1)
        stack.push(t[i])
        if (checkforsolution(board, checkhole)):
            return 1
        else:
            checkhole -= 1
            stack.pop()
            jump(board, t[i][0], 1)
            jump(board, t[i][1], 1)
            jump(board, t[i][2], 0)
    return (0)


print("Initial hole to be empty at:")
set_board = [[-1 for i in range(j + 1)] for j in range(5)]
stack = Stack()
while (1):
    init_hole = int(input())
    #print("Enter the maximum no. of pegs allowed to be left")
    pegs_allowed = 1
    peg = 14 - pegs_allowed
    if (init_hole < 15 and init_hole > -1 and pegs_allowed > 0 and pegs_allowed < 13):
        set_board = findmyrow(set_board, init_hole)
        checkforsolution(set_board, 0)
        break
    else:
        print("Invalid Input. Enter the maximum no. of pegs allowed to be left again")
