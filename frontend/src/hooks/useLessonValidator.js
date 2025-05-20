import { useState } from 'react'

export function useLessonValidator(schema) {
    const [formDataList, setFormDataList] = useState([
        { title: '', descriptionC: '', youtubeLink: '' }
    ])
    const [fieldErrorsList, setFieldErrorsList] = useState([{}])

    const handleChange = (index, e) => {
        const { name, value } = e.target

        const pureName = name.split('-')[0]

        const updatedLesson = {
            ...formDataList[index],
            [pureName]: value
        }

        const newFormDataList = [...formDataList]
        newFormDataList[index] = updatedLesson
        setFormDataList(newFormDataList)

        const fieldSchema = schema.shape[pureName]
        const fieldValidation = fieldSchema.safeParse(value)

        const newFieldErrorsList = [...fieldErrorsList]
        const currentErrors = { ...fieldErrorsList[index] }

        if (!fieldValidation.success) {
            currentErrors[pureName] = fieldValidation.error.errors[0].message
        } else {
            delete currentErrors[pureName]
        }

        newFieldErrorsList[index] = currentErrors
        setFieldErrorsList(newFieldErrorsList)
    }

    const addLesson = () => {
        setFormDataList([...formDataList, { title: '', descriptionC: '', youtubeLink: '' }])
        setFieldErrorsList([...fieldErrorsList, {}])
    }

    const removeLesson = (index) => {
        if (formDataList.length > 1) {
            const newFormDataList = formDataList.filter((_, i) => i !== index)
            const newFieldErrorsList = fieldErrorsList.filter((_, i) => i !== index)
            setFormDataList(newFormDataList)
            setFieldErrorsList(newFieldErrorsList)
        }
    }

    return {
        formDataList,
        fieldErrorsList,
        handleChange,
        addLesson,
        removeLesson
    }
}
