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
  bioperize([operations, values]) {
    if (!operations?.length) {
      throw Error('no operations received');
    }
    if (!values?.length) {
      throw Error('no values received');
    }

    return values.reduce((prev, curr, i) => {
      const op = operations[i - 1] ?? operations[operations.length - 1];
      return op(prev, curr);
    });
  },
};

module.exports = Calculator;
