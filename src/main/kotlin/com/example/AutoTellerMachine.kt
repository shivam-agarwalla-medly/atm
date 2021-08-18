package com.example

class AutoTellerMachine(private val printer: Printer, private val bankingService: BankingService) {
    fun withdraw(amount: Int) {
        bankingService.withdraw(amount)
        printer.print("$amount Withdrawn")
    }
}