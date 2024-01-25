import androidx.lifecycle.ViewModel
import com.example.realestatemanager.model.LoanData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class LoanViewModel : ViewModel() {
    private val _loanData = MutableStateFlow(LoanData(0.0, 0.0, 0.0, 0))
    val loanData: StateFlow<LoanData> = _loanData

    fun updateLoanData(loanData: LoanData) {
        _loanData.value = loanData
    }

    private fun calculateMonthlyPayment(loanData: LoanData): Double {
        // Implémentez la logique du calcul du paiement mensuel ici
        val interest = loanData.amount * loanData.downPayment / 100
        return loanData.downPayment - interest
    }

    private fun calculateTotalPayment(loanData: LoanData): Double {
        // Implémentez la logique du calcul du paiement total ici
        return 0.0
    }

    private fun calculateTotalInterest(loanData: LoanData): Double {
        // Implémentez la logique du calcul du total des intérêts ici
        return 0.0
    }
}