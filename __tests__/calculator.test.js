const C = require('../src/calculator');

describe('Calculator', () => {
  describe('addition', () => {
    it.each([
      { n: [4, 5], r: 9 },
      { n: [4, 5, 4, 5], r: 18 },
      { n: [0, 1, 2, 3, 4], r: 10 },
      { n: [0, 0, 0, 0, 0], r: 0 },
    ])('should add and result $r', ({ n, r }) => {
      expect(C.add(...n)).toBe(r);
    });
  });
});
