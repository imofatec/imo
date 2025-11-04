import React, { useState } from 'react';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { certificateSchema } from "../schemas/certificate";
import { CertificateCodeForm } from '../components/validateCertificate/certificateCodeForm';
import { CertificateDetails } from '../components/validateCertificate/certificateDetails';

export default function ValidateCertificate() {
    const [current, setCurrent] = useState('code');

    const { control, handleSubmit } = useForm({
        resolver: zodResolver(certificateSchema)
    })

    const certificateData = {
        authorName: "João Silva",
        courseName: "Introdução ao React Native",
        IniciationDate: "2024-01-15",
        completionDate: "2024-06-15",
    }

    async function onSubmit(data) {
        console.log("Validating certificate with data:", data);
        setCurrent('details');
    }
    return current === "code" ? (
        <CertificateCodeForm
            control={control}
            onSubmit={handleSubmit(onSubmit)}
        />
    ) : (
        <CertificateDetails
            certificateData={certificateData}
            onBack={() => setCurrent("code")}
        />
    );
}
