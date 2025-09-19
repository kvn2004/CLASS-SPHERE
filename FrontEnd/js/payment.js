let token = null; // JWT token

// Get JWT token on page load
window.onload = async () => {
    try {
        const cookie = await cookieStore.get("token");
        if (cookie && cookie.value) {
            token = cookie.value;
            loadRecentPayments(); // fetch recent payments on page load
        } else {
            window.location.href = "../index.html";
        }
    } catch (err) {
        console.error(err);
        window.location.href = "../index.html";
    }
};

// Load courses for a student
$('#loadCoursesBtn').on('click', function () {
    const studentId = $('#studentIdInput').val().trim();
    if (!studentId) return Swal.fire('Enter Student ID');

    $('#courseSelect').empty().append('<option value="">-- Loading courses --</option>');

    $.ajax({
        url: `http://localhost:8080/api/payments/student/${studentId}/courses`,
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` },
        success: function (courses) {
            $('#courseSelect').empty().append('<option value="">-- Select Course --</option>');
            courses.forEach(c => {
                $('#courseSelect').append(`<option value="${c.courseId}">${c.title}</option>`);
            });
        },
        error: function (xhr) {
            console.error(xhr);
            Swal.fire('Error loading courses', xhr.responseJSON?.message || xhr.statusText, 'error');
        }
    });
});

// Fetch course fee
$('#courseSelect').on('change', function () {
    const courseId = $(this).val();
    if (!courseId) {
        $('#amount').val('');
        return;
    }

    $.ajax({
        url: `http://localhost:8080/api/payments/course/${courseId}/fee`,
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` },
        success: function (fee) {
            $('#amount').val(fee);
        },
        error: function (xhr) {
            console.error('Failed to fetch course fee:', xhr);
            $('#amount').val('');
            Swal.fire('Error fetching course fee', xhr.responseJSON?.message || xhr.statusText, 'error');
        }
    });
});

// Submit payment (CASH/CARD)
$('#paymentForm').on('submit', function (e) {
    e.preventDefault();
    const studentId = $('#studentIdInput').val().trim();
    const courseId = $('#courseSelect').val();
    const paymentMonth = $('#paymentMonth').val();
    const amount = $('#amount').val().trim();
    const paymentMethod = $('#paymentMethod').val();
    const notes = $('#notes').val();

    if (!studentId || !courseId || !paymentMonth || !amount || !paymentMethod) {
        return Swal.fire('Please fill all required fields');
    }

    const paymentDate = paymentMonth + '-01';
    const amountValue = parseFloat(amount);
    if (isNaN(amountValue) || amountValue <= 0) {
        return Swal.fire('Invalid payment amount');
    }

    if (paymentMethod === 'ONLINE') return; // Online handled separately

    const dto = { studentId, courseId, paymentMonth: paymentDate, amount: amountValue, paymentMethod, notes };

    $.ajax({
        url: 'http://localhost:8080/api/payments',
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(dto),
        success: function (res) {
            Swal.fire('Payment saved!', `Payment ID: ${res.paymentId}`, 'success');
            $('#paymentForm')[0].reset();
            loadRecentPayments();
        },
        error: function (xhr) {
            console.error(xhr);
            Swal.fire('Error saving payment', xhr.responseJSON?.message || xhr.statusText, 'error');
        }
    });
});


// PayHere button click
$('#payHereBtn').on('click', function () {
    const studentId = "S123"; // Example
    const courseId = "C001";
    const amount = 5000.00; // Example fee

    const orderId = "ORDER" + Date.now();
    
    // Request hash from backend
    $.ajax({
        url: "http://localhost:8080/api/payments/generate-hash",
        method: "POST",
        contentType: "application/json",
        headers: { 'Authorization': `Bearer ${token}` },
        data: JSON.stringify({ orderId, amount, currency: "LKR" }),
        success: function(res) {
            const payment = {
                sandbox: true, // Must be true for testing
                merchant_id: res.merchantId,
                return_url: "http://127.0.0.1:5500/payments-success.html",
                cancel_url: "http://127.0.0.1:5500/payments-cancel.html",
                notify_url: "http://127.0.0.1:8080/api/payments/notify",
                order_id: orderId,
                items: "Course Fee",
                amount: "1000.00", // 2 decimals
                currency: "LKR",
                hash: res.hash,
                first_name: "Student",
                last_name: studentId,
                email: "student@email.com",
                phone: "0771234567",
                address: "Colombo",
                city: "Colombo",
                country: "Sri Lanka",
                onCompleted: function(orderId) {
                    console.log("Payment completed:", orderId);
                    alert("Payment completed: " + orderId);
                },
                onDismissed: function() {
                    console.log("Payment cancelled");
                },
                onError: function(err) {
                    console.error("PayHere error:", err);
                }
            };
            payhere.startPayment(payment);
        },
        error: function(err) {
            console.error("Failed to generate hash:", err);
        }
    });
});

// Load recent payments
function loadRecentPayments(limit = 5) {
    $.ajax({
        url: `http://localhost:8080/api/payments/recent?limit=${limit}`,
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` },
        success: function (payments) {
            const tbody = $('#paymentsTable tbody');
            tbody.empty();
            if (payments.length === 0) {
                tbody.append('<tr><td colspan="5" class="text-center text-muted">No recent payments found</td></tr>');
                return;
            }
            payments.forEach(p => {
                tbody.append(`
                    <tr>
                        <td>${p.studentId}</td>
                        <td>Rs. ${p.amount.toFixed(2)}</td>
                        <td>${p.paymentMonth}</td>
                        <td>${p.paymentMethod}</td>
                        <td><span class="badge bg-success">Paid</span></td>
                    </tr>
                `);
            });
        },
        error: function (xhr) {
            console.error('Failed to load recent payments:', xhr);
        }
    });
}
