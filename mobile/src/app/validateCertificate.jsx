import React, { useState } from 'react';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { certificateSchema } from "../schemas/certificate";
import { CertificateCodeForm } from '../components/validateCertificate/certificateCodeForm';
import { CertificateDetails } from '../components/validateCertificate/certificateDetails';
import { certificateRequest } from '../requests/certificate/certificateRequest';
import { formatDateTime } from "../utils/date";
import { set } from 'zod';

export default function ValidateCertificate() {
    const [errorMessage, setErrorMessage] = useState(null);
    const [current, setCurrent] = useState('code');
    const [certificateData, setCertificateData] = useState(null);
    const [loading, setLoading] = useState(false);

    const { control, handleSubmit } = useForm({
        resolver: zodResolver(certificateSchema)
    })

    async function onSubmit(data) {
        setErrorMessage(null);
        setLoading(true);
        const response = await certificateRequest(data.id);
        setLoading(false);
        if (!response.success) {
            setErrorMessage(response.error);
            return;
        }
        const apiData = response.data;
        const formatted = {
            authorName: apiData.user.name,
            courseName: apiData.course.name.name,
            courseStartedAt: formatDateTime(apiData.certificate.certificatePeriod.courseStartedAt),
            courseFinishedAt: formatDateTime(apiData.certificate.certificatePeriod.courseFinishedAt),
            issuedAt: formatDateTime(apiData.certificate.issuedAt),
        };
        setCertificateData(formatted);
        setCurrent('details');
    }

    return current === "code" ? (
        <CertificateCodeForm
            control={control}
            onSubmit={handleSubmit(onSubmit)}
            errorMessage={errorMessage}
            isloading={loading}
        />
    ) : (
        <CertificateDetails
            certificateData={certificateData}
            onBack={() => setCurrent("code")}
        />
    );
}
