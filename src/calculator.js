const Calculator = {
  add(...values) {
    return values.reduce((prev, curr) => prev + curr, 0);
  },
  subtract(...values) {
    return values.reduce((prev, curr) => prev - curr);
  },
  multiply(...values) {
    return values.reduce((prev, curr) => prev * curr);
  },
  divide(...values) {
    return values.reduce((prev, curr) => prev / curr);
  },
  mod(...values) {
    return values.reduce((prev, curr) => prev % curr);
  },
  power(...values) {
    return values.reduce((prev, curr) => prev ** curr);
  },
};

module.exports = Calculator;
