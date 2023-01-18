import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentEditProfileBinding
import com.sm.sharemobilityapp.databinding.FragmentProfileBinding
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var type: String

    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        val recyclerView = binding.recyclerView
//        recyclerView.setHasFixedSize(true)

        binding.apply {
            viewModel = userViewModel
        }

        /*
         * Load profile of the logged user
         */
        userViewModel.userInfo.observe(viewLifecycleOwner) { response ->
            if(response != null) {
                binding.profileName.setText(response.firstname + " " + response.lastname)
                binding.profileAddress.setText(response.address)
                binding.profileEmail.setText(response.username)
                binding.profilePassword.setText(response.password)
                type = response.userType!!
            }
        }

        /*
         * Edit profile of the logged user
         * !!!ID AND TYPE HAVE TO BE NULL, BECAUSE ONCE CREATED THEY SHOULD NOT BE CHANGED!!!
         */

        binding.editButton.setOnClickListener {
            val userInfo = UserInfo(null,
                type,
                binding.profileEmail.text.toString(),
                binding.profilePassword.text.toString(),
                binding.profileName.text!!.split(" ")[0],
                binding.profileName.text!!.split(" ")[1],
                binding.profileAddress.text.toString(),

            )
            userViewModel.updateUser(userInfo)
            view.findNavController().navigate(R.id.action_editProfileFragment_to_profile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}