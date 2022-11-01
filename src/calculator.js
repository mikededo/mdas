const Calculator = {
  add(...values) {
    return values.reduce((prev, curr) => prev + curr, 0);
  },
  subtract(...values) {
    return values.reduce((prev, curr) => prev - curr, 0);
  },
};

module.exports = Calculator;
