import random
import pygame

class Badguy:

    def __init__(self):
        side = random.randint(0, 3)
        if (side == 0):
            x = random.randint(0, 450)
            y = -150
            self.vX = 0
            self.vY = random.randint(3, 7)
        elif (side == 1):
            x = random.randint(0, 450)
            y = 650
            self.vX = 0
            self.vY = -random.randint(3, 7)
        elif (side == 2):
            x = -150
            y = random.randint(0, 450)
            self.vX = random.randint(3, 7)
            self.vY = 0
        elif (side == 3):
            x = 650
            y = random.randint(0, 450)
            self.vX = -random.randint(3, 7)
            self.vY = 0
        self.hitbox = pygame.Rect(x, y, 50, 50)
        

    def draw(self, s):
        if (self.vY == 0):
            pygame.draw.rect(s, (255, 200, 200), (0, self.hitbox.y, 500, 50))
        if (self.vX == 0):
            pygame.draw.rect(s, (255, 200, 200), (self.hitbox.x, 0, 50, 500))
        pygame.draw.rect(s, (255, 0, 0), self.hitbox)
        

    def get_hitbox(self):
        return self.hitbox
    def update(self):
        self.hitbox.x += self.vX
        self.hitbox.y += self.vY
        
    def is_dead(self):
        if self.hitbox.x < -200:
            return True
        if self.hitbox.x > 700:
            return True
        if self.hitbox.y < -200:
            return True
        if self.hitbox.y > 700:
            return True
        return False
