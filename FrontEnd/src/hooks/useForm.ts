import React, { useCallback, useMemo, useState } from "react";

interface UseFormReturn<F> {
    form: F;
    setForm: React.Dispatch<React.SetStateAction<F>>;
    setField: (name: keyof F | string, value: any) => void;
    registerControl: (name: keyof F | string) => RegisterControlReturn;
    formIsValid: boolean;
    resetForm: () => void;
}

interface UseFormProps<F> {
    initialForm: F;
    requiredFields?: (keyof F | string)[]; // Optional list of required fields
}

interface RegisterControlReturn {
    value: any;
    name: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    error?: boolean;
    helperText?: string;
}

export const useForm = <F extends Record<string, any>>(props: UseFormProps<F>): UseFormReturn<F> => {
    const { initialForm, requiredFields = [] } = props; // Default to no required fields
    const [form, setForm] = useState<F>(initialForm);
    const [errors, setErrors] = useState<Record<string, string>>({});

    // Helper to safely update nested fields in the form
    const setNestedField = (obj: Record<string, any>, path: string[], value: any): Record<string, any> => {
        if (path.length === 1) {
            return { ...obj, [path[0]]: value };
        }
        const [first, ...rest] = path;
        return {
            ...obj,
            [first]: setNestedField(obj[first] || {}, rest, value),
        };
    };

    // Set field value dynamically (handles nested fields)
    const setField = useCallback((name: keyof F | string, value: any) => {
        const path = name.toString().split(".");
        setForm((prevForm) => {
            // Ensure the form structure is properly typed
            const updatedForm = setNestedField(prevForm as Record<string, any>, path, value);
            return updatedForm as F;
        });

        // Validate field and update errors
        const errorMessage = validateField(name as string, value);
        setErrors((prevErrors) => ({
            ...prevErrors,
            [name as string]: errorMessage,
        }));
    }, []);

    // Get value of a nested field dynamically
    const getNestedValue = (obj: Record<string, any>, path: string[]): any => {
        return path.reduce((acc, key) => acc?.[key], obj);
    };

    // Register a control for a form field
    const registerControl = (name: keyof F | string): RegisterControlReturn => {
        const path = name.toString().split(".");
        return {
            value: getNestedValue(form, path) || "",
            name: name as string,
            onChange: (e) => setField(name, e.target.value),
            error: !!errors[name as string],
            helperText: errors[name as string],
        };
    };

    // Basic field validation logic (customized for optional/required fields)
    const validateField = (name: string, value: any): string => {
        if (requiredFields.includes(name) && (value === "" || value === undefined || value === null)) {
            return "This field is required.";
        }
        return ""; // No error for optional fields
    };

    // Reset form to its initial state
    const resetForm = () => {
        setForm(initialForm);
        setErrors({});
    };

    // Check if the form is valid
    const formIsValid = useMemo(() => {
        const validationErrors: Record<string, string> = {};
        for (const key in form) {
            const errorMessage = validateField(key, form[key]);
            if (errorMessage) {
                validationErrors[key] = errorMessage;
            }
        }
        setErrors(validationErrors);
        return Object.keys(validationErrors).length === 0;
    }, [form]);

    return {
        form,
        setForm,
        setField,
        registerControl,
        formIsValid,
        resetForm,
    };
};
