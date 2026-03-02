<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
            background-color : radial-gradient
        }

        body {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;

            /* shadcn-like subtle gradient */
            background-color: #f8fafc;
            background-image:
                radial-gradient(at 40% 20%, rgba(148,163,184,0.15) 0px, transparent 50%),
                radial-gradient(at 80% 80%, rgba(99,102,241,0.08) 0px, transparent 50%);
        }

        .card {
            background: #ffffff;
            width: 400px;
            padding: 40px;
            border-radius: 12px;
            border: 1px solid #e2e8f0;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
        }

        h2 {
            font-size: 22px;
            font-weight: 600;
            color: #0f172a;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-size: 14px;
            color: #334155;
            margin-bottom: 6px;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            font-size: 14px;
            background-color: #ffffff;
            transition: border 0.2s ease, box-shadow 0.2s ease;
        }

        input:focus {
            outline: none;
            border-color: #6366f1;
            box-shadow: 0 0 0 3px rgba(99,102,241,0.15);
        }

        button {
            width: 100%;
            padding: 11px;
            border: none;
            border-radius: 8px;
            background-color: #111827;
            color: #ffffff;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        button:hover {
            background-color: #1f2937;
        }
    </style>
</head>


<body>
<div class="card">
    <h2>Create Account</h2>
    <form action="/register" method="post">
        <div class="form-group">
            <label for="name">Full Name</label>
            <input id="name" type="text" name="name" required />
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input id="email" type="email" name="email" required />
        </div>

        <button type="submit">Register</button>
    </form>
</div>
</body>

</html>