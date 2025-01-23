// package com.example.hotel_system.controllers;

// import com.example.hotel_system.models.Bill;
// import com.example.hotel_system.services.BillService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// @Controller
// @RequestMapping("/receptionist/bills")
// public class BillController {

//     @Autowired
//     private BillService billService;

//     @GetMapping("")
//     public String billManagementPage(Model model) {
//         // Add user info to the model
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         model.addAttribute("username", auth.getName());
//         model.addAttribute("role", auth.getAuthorities().toString());

//         // Return the bill management page
//         return "receptionist/bill-management";
//     }

//     @PostMapping("/generate")
//     public String handleGenerateBill(@RequestParam String bookingId, Model model) {
//         try {
//             Bill bill = billService.generateBill(bookingId);
//             return "redirect:/receptionist/bills/view/" + bill.getId();
//         } catch (RuntimeException e) {
//             model.addAttribute("errorMessage", e.getMessage());
//             return "receptionist/error";
//         }
//     }

//     @GetMapping("/generate/{bookingId}")
//     public String generateBill(@PathVariable String bookingId, Model model) {
//         try {
//             Bill bill = billService.generateBill(bookingId);
//             model.addAttribute("bill", bill);
//             return "receptionist/bill-details";
//         } catch (RuntimeException e) {
//             model.addAttribute("errorMessage", e.getMessage());
//             return "receptionist/error";
//         }
//     }

//     @GetMapping("/view/{bookingId}")
//     public String viewBill(@PathVariable String bookingId, Model model) {
//         Bill bill = billService.getBillByBookingId(bookingId);
//         if (bill == null) {
//             model.addAttribute("errorMessage", "No bill found for this booking.");
//             return "receptionist/error";
//         }
//         model.addAttribute("bill", bill);
//         return "receptionist/bill-details";
//     }

//     @GetMapping("/view-by-id/{id}")
//     public String viewBillById(@PathVariable String id, Model model) {
//         Bill bill = billService.getBillById(id);
//         if (bill == null) {
//             model.addAttribute("errorMessage", "No bill found with ID: " + id);
//             return "receptionist/error";
//         }
//         model.addAttribute("bill", bill);
//         return "receptionist/bill-details";
//     }
// }


// package com.example.hotel_system.controllers;

// import com.example.hotel_system.models.Bill;
// import com.example.hotel_system.services.BillService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @Controller
// public class BillController {

//     @Autowired
//     private BillService billService;

//     // Receptionist endpoints
//     @GetMapping("/receptionist/bills")
//     public String billManagementPage(Model model) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         model.addAttribute("username", auth.getName());
//         model.addAttribute("role", auth.getAuthorities().toString());
//         return "receptionist/bill-management";
//     }

//     @PostMapping("/receptionist/bills/generate")
//     public String handleGenerateBill(@RequestParam String bookingId, Model model) {
//         try {
//             Bill bill = billService.generateBill(bookingId);
//             return "redirect:/receptionist/bills/view/" + bill.getId();
//         } catch (RuntimeException e) {
//             model.addAttribute("errorMessage", e.getMessage());
//             return "receptionist/error";
//         }
//     }

//     @GetMapping("/receptionist/bills/generate/{bookingId}")
//     public String generateBill(@PathVariable String bookingId, Model model) {
//         try {
//             Bill bill = billService.generateBill(bookingId);
//             model.addAttribute("bill", bill);
//             return "receptionist/bill-details";
//         } catch (RuntimeException e) {
//             model.addAttribute("errorMessage", e.getMessage());
//             return "receptionist/error";
//         }
//     }

//     @GetMapping("/receptionist/bills/view/{bookingId}")
//     public String viewBill(@PathVariable String bookingId, Model model) {
//         Bill bill = billService.getBillByBookingId(bookingId);
//         if (bill == null) {
//             model.addAttribute("errorMessage", "No bill found for this booking.");
//             return "receptionist/error";
//         }
//         model.addAttribute("bill", bill);
//         return "receptionist/bill-details";
//     }

//     @GetMapping("/receptionist/bills/view-by-id/{id}")
//     public String viewBillById(@PathVariable String id, Model model) {
//         Bill bill = billService.getBillById(id);
//         if (bill == null) {
//             model.addAttribute("errorMessage", "No bill found with ID: " + id);
//             return "receptionist/error";
//         }
//         model.addAttribute("bill", bill);
//         return "receptionist/bill-details";
//     }

//     // Guest endpoints
//     @GetMapping("/guest/bills")
//     public String viewMyBills(Model model) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         String userId = auth.getName();
        
//         List<Bill> bills = billService.getBillsByUserId(userId);
        
//         model.addAttribute("bills", bills);
//         model.addAttribute("username", auth.getName());
//         model.addAttribute("role", auth.getAuthorities().toString());
        
//         return "guest/my-bills";
//     }

//     @GetMapping("/guest/bills/view/{id}")
//     public String viewGuestBillDetails(@PathVariable String id, Model model) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         String userId = auth.getName();
        
//         Bill bill = billService.getBillById(id);
        
//         if (bill == null || !bill.getUserId().equals(userId)) {
//             model.addAttribute("errorMessage", "Bill not found or access denied");
//             return "error";
//         }
        
//         model.addAttribute("bill", bill);
//         model.addAttribute("username", auth.getName());
//         model.addAttribute("role", auth.getAuthorities().toString());
        
//         return "guest/bill-details";
//     }

//     @PostMapping("/guest/bills/pay/{id}")
//     public String payBill(@PathVariable String id, @RequestParam String paymentMethod) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         String userId = auth.getName();
        
//         try {
//             Bill bill = billService.getBillById(id);
            
//             if (bill == null || !bill.getUserId().equals(userId)) {
//                 return "redirect:/guest/bills?error=Bill not found or access denied";
//             }
            
//             if (bill.getPaymentStatus() == Bill.PaymentStatus.PAID) {
//                 return "redirect:/guest/bills?error=Bill is already paid";
//             }
            
//             bill.setPaymentStatus(Bill.PaymentStatus.PAID);
//             billService.updateBill(bill);
            
//             return "redirect:/guest/bills?success=true";
//         } catch (Exception e) {
//             return "redirect:/guest/bills?error=" + e.getMessage();
//         }
//     }
// }

package com.example.hotel_system.controllers;

import com.example.hotel_system.models.Bill;
import com.example.hotel_system.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    // Receptionist endpoints
    @GetMapping("/receptionist/bills")
    public String billManagementPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Bill> allBills = billService.getAllBills();
        
        model.addAttribute("bills", allBills);
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        return "receptionist/bill-management";
    }

    @PostMapping("/receptionist/bills/generate")
    public String handleGenerateBill(@RequestParam String bookingId, Model model) {
        try {
            Bill bill = billService.generateBill(bookingId);
            return "redirect:/receptionist/bills/view/" + bill.getId();
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "receptionist/error";
        }
    }

    @GetMapping("/receptionist/bills/generate/{bookingId}")
    public String generateBill(@PathVariable String bookingId, Model model) {
        try {
            Bill bill = billService.generateBill(bookingId);
            model.addAttribute("bill", bill);
            return "receptionist/bill-details";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "receptionist/error";
        }
    }

    @GetMapping("/receptionist/bills/view/{bookingId}")
    public String viewBill(@PathVariable String bookingId, Model model) {
        Bill bill = billService.getBillByBookingId(bookingId);
        if (bill == null) {
            model.addAttribute("errorMessage", "No bill found for this booking.");
            return "receptionist/error";
        }
        model.addAttribute("bill", bill);
        return "receptionist/bill-details";
    }

    @GetMapping("/receptionist/bills/view-by-id/{id}")
    public String viewBillById(@PathVariable String id, Model model) {
        Bill bill = billService.getBillById(id);
        if (bill == null) {
            model.addAttribute("errorMessage", "No bill found with ID: " + id);
            return "receptionist/error";
        }
        model.addAttribute("bill", bill);
        return "receptionist/bill-details";
    }

    // Guest endpoints
    @GetMapping("/guest/bills")
    public String viewMyBills(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        List<Bill> bills = billService.getBillsByUserId(userId);
        
        model.addAttribute("bills", bills);
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        
        return "guest/my-bills";
    }

    @GetMapping("/guest/bills/view/{id}")
    public String viewGuestBillDetails(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        Bill bill = billService.getBillById(id);
        
        if (bill == null || !bill.getUserId().equals(userId)) {
            model.addAttribute("errorMessage", "Bill not found or access denied");
            return "error";
        }
        
        model.addAttribute("bill", bill);
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        
        return "guest/bill-details";
    }

    @PostMapping("/guest/bills/pay/{id}")
    public String payBill(@PathVariable String id, @RequestParam String paymentMethod) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        try {
            // Use the billService's processBillPayment method
            billService.processBillPayment(id, userId, paymentMethod);
            return "redirect:/guest/bills?success=true";
        } catch (Exception e) {
            return "redirect:/guest/bills?error=" + e.getMessage();
        }
    }
}