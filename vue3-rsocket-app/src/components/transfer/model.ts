class TransferDto {
    from: string | undefined
    to: string | undefined
    amount: number | undefined

    constructor(_amount: number) {
        this.from = "德刚"
        this.to = "Andy"
        this.amount = _amount
    }
}

export { TransferDto }