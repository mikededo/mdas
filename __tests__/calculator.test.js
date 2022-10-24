const Calculator = require("../calculator")

describe("Calculator", ()=> {

    it("should add", ()=>{

        const calculator = new Calculator(4, 5)
        expect(calculator.add()).toBe(9)
    })

})