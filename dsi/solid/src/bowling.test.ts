import { Game, type PinCount } from './bowling';

describe('bowling', () => {
  it('should return 0 for a 0 points game', () => {
    const g = new Game();
    for (let i = 0; i < 20; i++) {
      g.roll(0);
    }
    expect(g.score()).toBe(0);
  });

  it.each([
    {
      rolls: [
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
      ] as PinCount[],
      score: 20,
    },
    {
      rolls: [
        5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2,
      ] as PinCount[],
      score: 70,
    },
    {
      rolls: [
        5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 2, 5, 5, 2,
      ] as PinCount[],
      score: 75,
    },
    {
      rolls: [
        9, 1, 2, 5, 1, 2, 5, 5, 9, 0, 0, 3, 4, 4, 7, 2, 8, 1, 0, 0,
      ] as PinCount[],
      score: 79,
    },
    {
      rolls: [
        9, 1, 2, 5, 1, 2, 5, 5, 9, 0, 10, 4, 4, 7, 2, 8, 1, 0, 0,
      ] as PinCount[],
      score: 94,
    },
  ])('should return the correct score: $score', ({ rolls, score }) => {
    const g = new Game();
    rolls.forEach((r) => {
      g.roll(r);
    });
    expect(g.score()).toBe(score);
  });

  it('should return 300 for a prefect game', () => {
    const g = new Game();
    for (let i = 0; i < 13; i++) {
      g.roll(10);
    }
    expect(g.score()).toBe(300);
  });
});
