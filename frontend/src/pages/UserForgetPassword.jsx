import React, { useState } from 'react'
import { Titulo } from '@/components/ui/titulo'
import StepEmailForm from '@/components/ui/stepsNewPassword/StepEmailForm'
import StepVerificationForm from '@/components/ui/stepsNewPassword/StepVerificationForm'
import StepNewPasswordForm from '@/components/ui/stepsNewPassword/StepNewPasswordForm'

export default function UserForgetPassword() {
    const [step, setStep] = useState('email')
    const [userId, setUserId] = useState(null)
    const [verificationCode, setVerificationCode] = useState(null)


    return (
        <>
            <Titulo titulo="Redefinir senha / IMO" />
            {step === 'email' && (
                <StepEmailForm
                    onSuccess={(id) => {
                        setUserId(id)
                        setStep('verify')
                    }}
                />
            )}

            {step === 'verify' && (
                <StepVerificationForm
                    userId={userId}
                    onSuccess={(VerificationCode) => {
                        setVerificationCode(VerificationCode)
                        setStep('password')
                    }}
                />
            )}

            {step === 'password' && (
                <StepNewPasswordForm userId={userId} VerificationCode={verificationCode} />
            )}
        </>
    )
}
