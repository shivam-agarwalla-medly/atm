package com.example

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AutoTellerMachineTest : StringSpec({
    "should call banking service with the right amount when ATM withdraw is called"{
        val bankingService = mockk<BankingService>()
        val printer = mockk<Printer>(relaxed = true)
        val amount = 1000
        every { bankingService.withdraw(amount) } returns Unit

        AutoTellerMachine(printer, bankingService).withdraw(amount)

        verify { bankingService.withdraw(amount) }
    }

    "should print a receipt if money is withdrawn successfully"{
        val printer = mockk<Printer>(relaxed = true)
        val bankingService = mockk<BankingService>()
        val amount = 1000
        every { bankingService.withdraw(amount) } returns Unit

        AutoTellerMachine(printer, bankingService).withdraw(amount)

        verify { printer.print("$amount Withdrawn") }
    }

    "should throw exception if banking service throws an exception"{
        val printer = mockk<Printer>(relaxed = true)
        val bankingService = mockk<BankingService>()
        val amount = 1000
        every { bankingService.withdraw(amount) } throws Exception("Transaction Failed")

        val exception = shouldThrow<Exception> {
            AutoTellerMachine(printer, bankingService).withdraw(amount)
        }
        exception.message shouldBe "Transaction Failed"
    }
})