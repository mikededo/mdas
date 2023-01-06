export type PinCount = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10;

export class Game {
  constructor(
    private frames: PinCount[] = [],
    private frameCount: number = 0,
  ) {}

  roll(pin: PinCount): void {
    this.frames[this.frameCount] = pin;
    this.frameCount++;
  }

  score(): number {
    let score = 0;
    for (let i = 0; i < this.frames.length - 1; ) {
      if (this.frames[i] === 10) {
        score += 10 + this.frames[i + 1] + this.frames[i + 2];
        i++;
      } else {
        if (this.frames[i] + this.frames[i + 1] === 10) {
          score += 10 + this.frames[i + 2];
        } else {
          score += this.frames[i] + this.frames[i + 1];
        }
        i += 2;
      }
    }
    return score;
  }
}
