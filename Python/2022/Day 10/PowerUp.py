import pygame
import random

class PowerUp:

    def __init__(self):
        self.hitbox = pygame.Rect(random.randint(0, 475), random.randint(0,475), 25, 25)
        self.type = random.randint(1,4)
        if (self.type == 1):
            self.color = (0, 255, 0)
        elif (self.type==2):
            self.color = (0, 0, 255)
        elif (self.type==3):
            self.color = (0, 0, 150)
        elif (self.type==4):
            self.color = (0, 255, 255)

    def get_hitbox(self):
        return self.hitbox

    def draw(self, s):
        pygame.draw.rect(s, self.color, self.hitbox)

    def get_type(self):
        return self.type
