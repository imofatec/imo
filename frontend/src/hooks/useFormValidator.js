import { useState } from 'react'

export function useFormValidator(schema, initialData = {}) {
  const [formData, setFormData] = useState(initialData)
  const [fieldErrors, setFieldErrors] = useState({})

  const handleChange = (e) => {
    const { name, value } = e.target
    const updatedForm = { ...formData, [name]: value }

    setFormData(updatedForm)

    const result = schema.safeParse(updatedForm)

    if (!result.success) {
      const errors = {}
      result.error.errors.forEach((err) => {
        errors[err.path[0]] = err.message
      })
      setFieldErrors(errors)
    } else {
      setFieldErrors({})
    }
  }

  return {
    formData,
    setFormData,
    fieldErrors,
    handleChange,
    setFieldErrors
  }
}