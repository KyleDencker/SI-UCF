# Kyle Dencker

import pygame, time

import platform2, platform_move, player, goal

def update(time):
    player.update(time)

    for p in plats:
        p.update(time)
        player.checkHit(p.getHitbox())
    

def draw(s):
    s.fill((0,0,0))
    for p in plats:
        p.draw(s)
    goals.draw(s)
    player.draw(s)
    #print(player.velo[1])

def loadLevel(level):
    global goals
    file = open("level_"+str(level)+".txt", "r")
    level_input = file.read()

    lines = level_input.split("\n")
    fl = lines[0].split(" ")
    goals = goal.goal(int(fl[0]), int(fl[1]), int(fl[2]), int(fl[3]))
    plats.append(platform2.platform(-100, 490, 790, 10))

    num_lines = int(lines[1])
    for line in range(2, num_lines+2):
        fl = lines[line].split(" ")
        plats.append(platform2.platform(int(fl[0]), int(fl[1]), int(fl[2]), int(fl[3])))
    move_lines = int(lines[num_lines+2])
    if (move_lines != 0):
        for line in range(num_lines+3, move_lines+num_lines+3):
            fl = lines[line].split(" ")
            plats.append(platform_move.platform_move(int(fl[0]), int(fl[1]), int(fl[2]), int(fl[3]), int(fl[4]), int(fl[5])))
    
    file.close()

    


def main():

    global plats, player, goals
    level = 6
    level_max = 6
    
    key_input = {
        "up": False,
        "down": False,
        "left": False,
        "right": False
    }

    
    
    plats = []

    player = player.player(pygame.Rect(300, 250, 10, 10))
    
    loadLevel(level)
    pygame.init()
    font = pygame.font.SysFont("comicsansms", 30)

    screen = pygame.display.set_mode( [500, 500] )
    running = True
    last_time = time.monotonic()

    while running == True:
        
        # Events (actions the user takes)
        for event in pygame.event.get():
            if (event.type == pygame.QUIT):
                running = False
                
            if event.type == pygame.MOUSEBUTTONUP:
                pass
                        
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    key_input['left'] = True
                if event.key == pygame.K_RIGHT:
                    key_input['right'] = True
                if event.key == pygame.K_UP:
                    key_input['up'] = True
                if event.key == pygame.K_DOWN:
                    key_input['down'] = True
           
            if event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT:
                    key_input['left'] = False
                if event.key == pygame.K_RIGHT:
                    key_input['right'] = False
                if event.key == pygame.K_UP:
                    key_input['up'] = False
                if event.key == pygame.K_DOWN:
                    key_input['down'] = False
            
        current_time = time.monotonic()
        diff_time = current_time - last_time
        last_time = current_time

        if key_input['up']:
            player.jump()
        if key_input['left']:
            player.left(diff_time)
        if key_input['right']:
            player.right(diff_time)

        if (player.getHitbox().colliderect(goals.getHitbox())):
            level+=1
            if (level > level_max):
                level = 1

            plats.clear()
            loadLevel(level)
        
        
        update(diff_time)
        draw(screen)
        
        pygame.display.flip()
    pygame.quit()

main()
