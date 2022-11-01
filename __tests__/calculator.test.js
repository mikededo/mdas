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

  describe('subtraction', () => {
    it.each([
      { n: [4, 5], r: -9 },
      { n: [4, 5, 4, 5], r: -18 },
      { n: [0, 1, 2, 3, 4], r: -10 },
      { n: [0, 0, 0, 0, 0], r: 0 },
      { n: [0, 1, -2, 3, -4], r: 2 },
    ])('should add and result $r', ({ n, r }) => {
      expect(C.subtract(...n)).toBe(r);
    });
  });

  describe('multiply', () => {
    it.each([
      { n: [1, 1, 1, 1], r: 1 },
      { n: [1, 2, 3, 4, 5], r: 120 },
      { n: [1, 2, 0, 3, 4], r: 0 },
      { n: [1], r: 1 },
    ])('should multiply and result $r', ({ n, r }) => {
      expect(C.multiply(...n)).toBe(r);
    });
  });

  describe('divide', () => {
    it.each([
      { n: [4, 2, 2], r: 1 },
      { n: [1, 1], r: 1 },
      { n: [10], r: 10 },
      { n: [10, 5, 0, 2], r: Infinity },
      { n: [0, 5], r: 0 },
    ])('should divide and result $r', ({ n, r }) => {
      expect(C.divide(...n)).toBe(r);
    });
  });

  describe('mod', () => {
    it.each([
      { n: [10, 0], r: NaN },
      { n: [10, 5], r: 0 },
      { n: [98765, 1000, 100, 10], r: 5 },
      { n: [98765, 1000], r: 765 },
      { n: [0, 10], r: 0 },
      { n: [100, 7, 4, 3], r: 2 },
    ])('should mod and result $r', ({ n, r }) => {
      expect(C.mod(...n)).toBe(r);
    });
  });
});
