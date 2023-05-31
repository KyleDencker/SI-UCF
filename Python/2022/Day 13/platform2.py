
import pygame


class platform:
    def __init__(self, x, y, w, h):
        self.hitbox = pygame.Rect(x, y, w, h)
        self.color = (0, 255, 0)

    def draw(self, s):
        pygame.draw.rect(s, self.color, self.hitbox)

    def getHitbox(self):
        return self.hitbox

    def update(self, delta_time):
        pass
