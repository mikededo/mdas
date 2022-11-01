const Calculator = {
  add(...values) {
    return values.reduce((prev, curr) => prev + curr, 0);
  },
  subtract(...values) {
    return values.reduce((prev, curr) => prev - curr, 0);
  },
  divide(...values) {
    return values.reduce((prev, curr, i) => (i === 0 ? curr : prev / curr));
  },
};

module.exports = Calculator;
