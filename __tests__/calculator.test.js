const Calculator = require('../src/calculator');

describe('Calculator', () => {
  it('should add', () => {
    const calculator = new Calculator(4, 5);
    expect(calculator.add()).toBe(9);
  });
});
