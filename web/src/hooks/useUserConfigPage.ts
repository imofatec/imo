import { useUser } from '@/contexts/UserContext'
import { showRequestErrorToast } from '@/lib/requestToast'
import {
  type UpdateUserBioData,
  type UpdateUserPasswordData,
  type UpdateUserProfileData,
  updateUserBioSchema,
  updateUserPasswordSchema,
  updateUserProfileSchema,
} from '@/schemas/user/updateUserSchema'
import { resendConfirmationEmailRequest } from '@/services/user/resendConfirmationEmailRequest'
import { updateUserRequest } from '@/services/user/updateUserRequest'
import { uploadPfpRequest } from '@/services/user/uploadPfpRequest'
import { zodResolver } from '@hookform/resolvers/zod'
import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import { toast } from 'sonner'

type UserConfigErrors = {
  photo: string | null
}

function normalizeBirthDate(value: string) {
  const trimmedValue = value.trim()

  if (!trimmedValue) return undefined

  if (/^\d{4}[-/]\d{2}[-/]\d{2}$/.test(trimmedValue)) {
    const [year, month, day] = trimmedValue.split(/[-/]/)
    return `${year}/${month}/${day}`
  }

  if (/^\d{2}\/\d{2}\/\d{4}$/.test(trimmedValue)) {
    const [day, month, year] = trimmedValue.split('/')
    return `${year}/${month}/${day}`
  }

  return trimmedValue
}

function resolveCurrentProfileImageSrc(
  profilePicturePath: string | null,
  photoPreviewUrl: string | null
) {
  if (photoPreviewUrl) {
    return photoPreviewUrl
  }

  return profilePicturePath?.trim() || null
}

export function useUserConfigPage() {
  const { user, refetch } = useUser()
  const [selectedPhoto, setSelectedPhoto] = useState<File | null>(null)
  const [photoPreviewUrl, setPhotoPreviewUrl] = useState<string | null>(null)
  const [isUploadingPhoto, setIsUploadingPhoto] = useState(false)
  const [isResendingConfirmation, setIsResendingConfirmation] = useState(false)
  const [errors, setErrors] = useState<UserConfigErrors>({
    photo: null,
  })

  const {
    register: registerProfile,
    handleSubmit: handleProfileSubmit,
    reset: resetProfileForm,
    setValue: setProfileValue,
    formState: { errors: profileErrors, isSubmitting: isSubmittingProfile },
  } = useForm<UpdateUserProfileData>({
    resolver: zodResolver(updateUserProfileSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
    defaultValues: {
      name: '',
      email: '',
      birthDate: '',
      availableTimePerDay: '',
      academicDegree: '',
      experienceLevel: '',
      categoryOfInterest1: '',
      categoryOfInterest2: '',
    },
  })

  const {
    register: registerBio,
    handleSubmit: handleBioSubmit,
    reset: resetBioForm,
    watch: watchBio,
    formState: { errors: bioErrors, isSubmitting: isSubmittingBio },
  } = useForm<UpdateUserBioData>({
    resolver: zodResolver(updateUserBioSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
    defaultValues: {
      bio: '',
    },
  })

  const {
    register: registerPassword,
    handleSubmit: handlePasswordSubmit,
    reset: resetPasswordForm,
    formState: { errors: passwordErrors, isSubmitting: isSubmittingPassword },
  } = useForm<UpdateUserPasswordData>({
    resolver: zodResolver(updateUserPasswordSchema),
    mode: 'onBlur',
    reValidateMode: 'onChange',
    defaultValues: {
      oldPassword: '',
      password: '',
      confPassword: '',
    },
  })

  useEffect(() => {
    setProfileValue('birthDate', user?.birthDate ?? '')
  }, [user?.birthDate, setProfileValue])

  useEffect(() => {
    resetBioForm({ bio: user?.bio ?? '' })
  }, [resetBioForm, user?.bio])

  useEffect(() => {
    return () => {
      if (photoPreviewUrl) {
        URL.revokeObjectURL(photoPreviewUrl)
      }
    }
  }, [photoPreviewUrl])

  function clearPhotoError() {
    setErrors((current) => ({ ...current, photo: null }))
  }

  function handleSelectPhoto(file: File | null) {
    clearPhotoError()

    if (photoPreviewUrl) {
      URL.revokeObjectURL(photoPreviewUrl)
    }

    if (!file) {
      setSelectedPhoto(null)
      setPhotoPreviewUrl(null)
      return
    }

    setSelectedPhoto(file)
    setPhotoPreviewUrl(URL.createObjectURL(file))
  }

  async function onSubmitProfile(data: UpdateUserProfileData) {
    try {
      const payload = {
        ...(data.name ? { name: data.name } : {}),
        ...(data.email ? { email: data.email } : {}),
        ...(data.birthDate?.trim() ? { birthDate: normalizeBirthDate(data.birthDate) } : {}),
        ...(data.availableTimePerDay ? { availableTimePerDay: data.availableTimePerDay } : {}),
        ...(data.academicDegree ? { academicDegree: data.academicDegree } : {}),
        ...(data.experienceLevel ? { experienceLevel: data.experienceLevel } : {}),
        ...([data.categoryOfInterest1, data.categoryOfInterest2].some(Boolean)
          ? {
              categoriesOfInterest: [data.categoryOfInterest1, data.categoryOfInterest2].filter(
                (category): category is string => Boolean(category)
              ),
            }
          : {}),
      }

      await updateUserRequest(payload)
       toast.success('Informações atualizadas', {
        id: 'user-profile-success',
        description: 'Suas informações foram atualizadas com sucesso.',
        duration: 3000,
      })
      await refetch()
      resetProfileForm()
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar atualizar suas informações. Por favor, tente novamente.',
        { id: 'user-profile-error' }
      )
    }
  }

  async function onSubmitBio(data: UpdateUserBioData) {
    try {
      const normalizedBio = data.bio.trim()

      await updateUserRequest({ bio: normalizedBio })
      toast.success('Bio atualizada', {
        id: 'user-bio-success',
        description: 'Sua bio foi atualizada com sucesso.',
        duration: 3000,
      })
      await refetch()
      resetBioForm({ bio: normalizedBio })
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar atualizar sua bio. Por favor, tente novamente.',
        { id: 'user-bio-error' }
      )
    }
  }

  async function onSubmitPassword(data: UpdateUserPasswordData) {
    try {
      await updateUserRequest({
        oldPassword: data.oldPassword,
        password: data.password,
      })

      toast.success('Senha atualizada', {
        id: 'user-password-success',
        description: 'Sua senha foi atualizada com sucesso.',
        duration: 3000,
      })
      resetPasswordForm()
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar atualizar sua senha. Por favor, tente novamente.',
        { id: 'user-password-error' }
      )
    }
  }

  async function handleUploadPhoto() {
    clearPhotoError()

    if (!selectedPhoto) {
      setErrors((current) => ({
        ...current,
        photo: 'Selecione uma foto antes de salvar.',
      }))
      return
    }

    setIsUploadingPhoto(true)

    try {
      await uploadPfpRequest({ file: selectedPhoto })
      await refetch()
      setSelectedPhoto(null)

      if (photoPreviewUrl) {
        URL.revokeObjectURL(photoPreviewUrl)
      }

      setPhotoPreviewUrl(null)
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar atualizar sua foto. Por favor, tente novamente.',
        { id: 'user-photo-error' }
      )
    } finally {
      setIsUploadingPhoto(false)
    }
  }

  async function handleResendConfirmationEmail() {
    setIsResendingConfirmation(true)

    try {
      if (!user?.email) return

      await resendConfirmationEmailRequest({ email: user.email })

      toast.success('E-mail reenviado', {
        id: 'user-confirmation-success',
        description: `Enviamos um novo e-mail de confirmação para ${user!.email}.`,
        duration: 3000,
      })
    } catch (error: unknown) {
      showRequestErrorToast(
        error,
        'Ocorreu um erro ao tentar reenviar o e-mail de confirmação. Por favor, tente novamente.',
        { id: 'user-confirmation-error' }
      )
    } finally {
      setIsResendingConfirmation(false)
    }
  }

  return {
    user,
    profileImageSrc: resolveCurrentProfileImageSrc(
      user?.profilePicturePath ?? null,
      photoPreviewUrl
    ),
    hasPendingPhoto: Boolean(selectedPhoto),
    isUploadingPhoto,
    isResendingConfirmation,
    registerProfile,
    handleProfileSubmit,
    profileErrors,
    isSubmittingProfile,
    registerBio,
    handleBioSubmit,
    bioErrors,
    isSubmittingBio,
    bioValue: watchBio('bio') ?? '',
    registerPassword,
    handlePasswordSubmit,
    passwordErrors,
    isSubmittingPassword,
    photoErrorMessage: errors.photo,
    handleSelectPhoto,
    handleUploadPhoto,
    handleResendConfirmationEmail,
    onSubmitProfile,
    onSubmitBio,
    onSubmitPassword,
  }
}
